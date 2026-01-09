package com.example.quanlyhusc.service.vnpay;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VnPayService {

    @Value("${vnpay.tmnCode}")
    private String tmnCode;

    @Value("${vnpay.hashSecret}")
    private String hashSecret;

    @Value("${vnpay.payUrl}")
    private String payUrl;

    @Value("${vnpay.returnUrl}")
    private String returnUrl;

    public String createPaymentUrl(String txnRef, long amountVnd, String orderInfo, String ipAddr) {
        // VNPAY: amount * 100 :contentReference[oaicite:2]{index=2}
        long amount = amountVnd * 100;

        // TreeMap => auto sort key tăng dần (checksum theo QueryString) :contentReference[oaicite:3]{index=3}
        Map<String, String> vnpParams = new TreeMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", tmnCode);
        vnpParams.put("vnp_Amount", String.valueOf(amount));
        vnpParams.put("vnp_CurrCode", "VND");
        vnpParams.put("vnp_TxnRef", txnRef);

        // orderInfo nên không dấu, không ký tự đặc biệt :contentReference[oaicite:4]{index=4}
        vnpParams.put("vnp_OrderInfo", orderInfo);
        vnpParams.put("vnp_OrderType", "other");
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_ReturnUrl", returnUrl);
        vnpParams.put("vnp_IpAddr", ipAddr);

        // time GMT+7 :contentReference[oaicite:5]{index=5}
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        vnpParams.put("vnp_CreateDate", sdf.format(cld.getTime()));
        cld.add(Calendar.MINUTE, 15);
        vnpParams.put("vnp_ExpireDate", sdf.format(cld.getTime()));

        // hashData + queryString phải “đồng bộ” cách encode để tránh lệch
        String hashData = buildQueryString(vnpParams, false);
        String queryString = buildQueryString(vnpParams, true);

        String secureHash = hmacSHA512(hashSecret, hashData);
        return payUrl + "?" + queryString + "&vnp_SecureHash=" + secureHash;
    }

    /**
     * Verify callback từ VNPAY.
     * - Truyền vào ALL vnp_* params lấy từ request (bao gồm vnp_SecureHash)
     * - Hàm tự remove hash fields để tính lại
     */
    public boolean verifyCallback(Map<String, String> allParams) {
        if (allParams == null) return false;

        String receivedHash = allParams.get("vnp_SecureHash");
        if (receivedHash == null || receivedHash.isBlank()) return false;

        Map<String, String> vnpParams = new TreeMap<>(allParams);
        vnpParams.remove("vnp_SecureHash");
        vnpParams.remove("vnp_SecureHashType");

        String hashData = buildQueryString(vnpParams, false);
        String calcHash = hmacSHA512(hashSecret, hashData);

        return calcHash.equalsIgnoreCase(receivedHash);
    }

    /**
     * @param urlEncode true => encode value để đưa lên URL
     *                 false => tạo data để ký (vẫn encode như query để đồng bộ)
     */
    private String buildQueryString(Map<String, String> params, boolean urlEncode) {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> e : params.entrySet()) {
            String k = e.getKey();
            String v = e.getValue();
            if (v == null || v.isBlank()) continue;

            sb.append(k).append("=");
            if (urlEncode) {
                sb.append(URLEncoder.encode(v, StandardCharsets.UTF_8));
            } else {
                // data ký: giữ đồng bộ encode giống query để tránh lệch bởi dấu cách, ký tự đặc biệt
                sb.append(URLEncoder.encode(v, StandardCharsets.UTF_8));
            }
            sb.append("&");
        }

        if (sb.length() > 0) sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    private String hmacSHA512(String key, String data) {
        try {
            Mac hmac512 = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmac512.init(secretKey);
            byte[] bytes = hmac512.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hash = new StringBuilder();
            for (byte b : bytes) hash.append(String.format("%02x", b));
            return hash.toString();
        } catch (Exception e) {
            throw new RuntimeException("Không thể tạo chữ ký VNPAY", e);
        }
    }
}

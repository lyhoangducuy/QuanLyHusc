package com.example.quanlyhusc.controller.sinhvien.ungho;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.quanlyhusc.service.vnpay.VnPayService;
import com.example.quanlyhusc.service.ungho.UngHoDonateService;

@Controller
public class VnPayCallbackController {

    @Autowired
    private VnPayService vnPayService;

    @Autowired
    private UngHoDonateService donateService;

    /* =======================
     * 1️⃣ RETURN URL
     * ======================= */
    @GetMapping("/vnpay/return")
    public String vnpayReturn(HttpServletRequest request, Model model) {

        Map<String, String> params = extractVnpParams(request);

        if (!vnPayService.verifyCallback(params)) {
            model.addAttribute("status", "INVALID_SIGNATURE");
            return "sinhvien/ungho/checkout";
        }

        String txnRef   = params.get("vnp_TxnRef");
        String respCode = params.get("vnp_ResponseCode");

        // ✅ UPDATE DB NGAY TẠI RETURN (demo OK)
        donateService.xuLyKetQuaVnpay(
                txnRef,
                respCode,
                params.get("vnp_TransactionNo"),
                params.get("vnp_BankCode"),
                params.get("vnp_PayDate"),
                params.get("vnp_SecureHash")
        );

        model.addAttribute("txnRef", txnRef);
        model.addAttribute("respCode", respCode);
        model.addAttribute("status", "00".equals(respCode) ? "SUCCESS" : "FAILED");

        return "sinhvien/ungho/checkout";
    }

    /* =======================
     * 2️⃣ IPN (CHUẨN NHẤT)
     * ======================= */
    @PostMapping("/vnpay/ipn")
    @ResponseBody
    public Map<String, String> vnpayIpn(HttpServletRequest request) {

        Map<String, String> params = extractVnpParams(request);
        Map<String, String> res = new HashMap<>();

        if (!vnPayService.verifyCallback(params)) {
            res.put("RspCode", "97");
            res.put("Message", "Invalid Signature");
            return res;
        }

        String txnRef   = params.get("vnp_TxnRef");
        String respCode = params.get("vnp_ResponseCode");

        // ✅ UPDATE DB TẠI IPN (PRODUCTION CHUẨN)
        donateService.xuLyKetQuaVnpay(
                txnRef,
                respCode,
                params.get("vnp_TransactionNo"),
                params.get("vnp_BankCode"),
                params.get("vnp_PayDate"),
                params.get("vnp_SecureHash")
        );

        res.put("RspCode", "00");
        res.put("Message", "Confirm Success");
        return res;
    }

    /* =======================
     * 3️⃣ Helper
     * ======================= */
    private Map<String, String> extractVnpParams(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        request.getParameterMap().forEach((key, value) -> {
            if (key.startsWith("vnp_") && value.length > 0) {
                map.put(key, value[0]);
            }
        });
        return map;
    }
}

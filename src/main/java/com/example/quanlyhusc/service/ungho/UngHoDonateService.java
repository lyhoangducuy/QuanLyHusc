package com.example.quanlyhusc.service.ungho;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.quanlyhusc.entity.NguoiDung;
import com.example.quanlyhusc.entity.ungho.GiaoDichUngHo;
import com.example.quanlyhusc.entity.ungho.UngHo;
import com.example.quanlyhusc.repository.ungho.GiaoDichUngHoRepository;
import com.example.quanlyhusc.repository.ungho.UngHoRepository;
import com.example.quanlyhusc.service.vnpay.VnPayService;

@Service
public class UngHoDonateService {

    @Autowired private UngHoRepository ungHoRepository;
    @Autowired private GiaoDichUngHoRepository gdRepo;
    @Autowired private VnPayService vnPayService;

    @Transactional
    public String taoGiaoDichVaLayUrl(Long ungHoId, Long soTienVnd, String noiDung, Long nguoiDungId, String ipAddr) {
        UngHo uh = ungHoRepository.findById(ungHoId).orElse(null);
        if (uh == null) throw new RuntimeException("Không tìm thấy ủng hộ");
        if (uh.getTrangThai() == null || uh.getTrangThai() == false) throw new RuntimeException("Chiến dịch đã đóng");
        if (soTienVnd == null || soTienVnd <= 0) throw new RuntimeException("Số tiền không hợp lệ");
        if (soTienVnd < 10000) throw new RuntimeException("Tối thiểu 10.000₫");

        // mã đơn nội bộ
        String ref = "UH" + System.currentTimeMillis(); // đủ unique cho đồ án

        GiaoDichUngHo gd = new GiaoDichUngHo();
        gd.setUngHo(uh);
        gd.setNguoiDungId(nguoiDungId);
        gd.setSoTienVnd(soTienVnd);
        gd.setNoiDung(noiDung);
        gd.setTrangThai("PENDING");
        gd.setMaDonHang(ref);
        gd.setVnpTxnRef(ref);
        gd.setTaoLuc(OffsetDateTime.now());
        gd.setCapNhatLuc(OffsetDateTime.now());
        gdRepo.save(gd);

        String orderInfo = "Ung ho #" + ungHoId + " - " + ref;
        return vnPayService.createPaymentUrl(ref, soTienVnd, orderInfo, ipAddr);
    }

    // update theo callback: SUCCESS/FAILED, idempotent
    @Transactional
    public void xuLyKetQuaVnpay(String vnpTxnRef, String responseCode, String transactionNo,
                                String bankCode, String payDate, String secureHash) {

        GiaoDichUngHo gd = gdRepo.findByVnpTxnRef(vnpTxnRef)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giao dịch"));

        // nếu đã SUCCESS rồi thì thôi (idempotent)
        if ("SUCCESS".equalsIgnoreCase(gd.getTrangThai())) return;

        gd.setVnpResponseCode(responseCode);
        gd.setVnpTransactionNo(transactionNo);
        gd.setVnpBankCode(bankCode);
        gd.setVnpPayDate(payDate);
        gd.setVnpSecureHash(secureHash);
        gd.setCapNhatLuc(OffsetDateTime.now());

        if ("00".equals(responseCode)) {
            gd.setTrangThai("SUCCESS");

            // cộng tiền vào campaign
            UngHo uh = gd.getUngHo();
            long newTotal = (uh.getDaNhanVnd() == null ? 0 : uh.getDaNhanVnd()) + gd.getSoTienVnd();
            uh.setDaNhanVnd(newTotal);
            uh.setCapNhatLuc(OffsetDateTime.now());
            ungHoRepository.save(uh);
        } else {
            gd.setTrangThai("FAILED");
        }

        gdRepo.save(gd);
    }
    public UngHo getCampaign(Long ungHoId) {
        return ungHoRepository.findById(ungHoId).orElse(null);
    }

    public Page<GiaoDichUngHo> pageGiaoDich(Long ungHoId, String trangThai, int pageNo, int pageSize) {
        PageRequest pageable = PageRequest.of(Math.max(pageNo - 1, 0), pageSize);

        if (trangThai != null && !trangThai.isBlank() && !"ALL".equalsIgnoreCase(trangThai)) {
            return gdRepo.findByUngHo_UngHoIdAndTrangThaiOrderByTaoLucDesc(ungHoId, trangThai.trim(), pageable);
        }
        return gdRepo.findByUngHo_UngHoIdOrderByTaoLucDesc(ungHoId, pageable);
    }

    public long countSuccess(Long ungHoId) {
        return gdRepo.countByUngHo_UngHoIdAndTrangThai(ungHoId, "SUCCESS");
    }

    public long countPending(Long ungHoId) {
        return gdRepo.countByUngHo_UngHoIdAndTrangThai(ungHoId, "PENDING");
    }

    public Long sumSuccess(Long ungHoId) {
        return gdRepo.sumSuccessByUngHoId(ungHoId);
    }
}

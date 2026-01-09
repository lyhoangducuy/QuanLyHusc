package com.example.quanlyhusc.entity.ungho;

import java.time.OffsetDateTime;

import com.example.quanlyhusc.entity.NguoiDung;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "giao_dich_ung_ho")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class GiaoDichUngHo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gd_id")
    private Long gdId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ung_ho_id", nullable = false)
    private UngHo ungHo;

    @JoinColumn(name = "nguoi_dung_id")
    private Long nguoiDungId;

    @Column(name = "so_tien_vnd", nullable = false)
    private Long soTienVnd;

    @Column(name = "noi_dung")
    private String noiDung;

    @Column(name = "trang_thai", nullable = false)
    private String trangThai = "PENDING";

    @Column(name = "ma_don_hang", nullable = false, unique = true)
    private String maDonHang;

    @Column(name = "vnp_txn_ref", nullable = false, unique = true)
    private String vnpTxnRef;

    @Column(name = "vnp_transaction_no")
    private String vnpTransactionNo;

    @Column(name = "vnp_response_code")
    private String vnpResponseCode;

    @Column(name = "vnp_bank_code")
    private String vnpBankCode;

    @Column(name = "vnp_pay_date")
    private String vnpPayDate;

    @Column(name = "vnp_secure_hash")
    private String vnpSecureHash;

    @Column(name = "tao_luc")
    private OffsetDateTime taoLuc = OffsetDateTime.now();

    @Column(name = "cap_nhat_luc")
    private OffsetDateTime capNhatLuc = OffsetDateTime.now();

    // getter / setter
}

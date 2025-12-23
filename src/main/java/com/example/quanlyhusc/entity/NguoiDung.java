package com.example.quanlyhusc.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "nguoi_dung")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class NguoiDung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nguoi_dung_id")
    private Long nguoiDungId;

    @Column(name = "ten_dang_nhap", unique = true, length = 50)
    private String tenDangNhap;

    @Column(name = "email", unique = true, length = 255)
    private String email;

    @Column(name = "so_dien_thoai", unique = true, length = 30)
    private String soDienThoai;

    @Column(name = "mat_khau_ma_hoa", nullable = false, length = 255)
    private String matKhauMaHoa;

    @Column(name = "ho_ten", nullable = false, length = 200)
    private String hoTen;

    @Column(name = "trang_thai_hoat_dong", nullable = false)
    private Boolean trangThaiHoatDong = true;

    @Column(name = "tao_luc", nullable = false)
    private OffsetDateTime taoLuc = OffsetDateTime.now();

    @Column(name = "cap_nhat_luc", nullable = false)
    private OffsetDateTime capNhatLuc = OffsetDateTime.now();
}

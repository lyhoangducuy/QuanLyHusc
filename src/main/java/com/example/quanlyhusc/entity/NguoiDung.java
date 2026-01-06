package com.example.quanlyhusc.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Set;

import com.example.quanlyhusc.entity.chat.CuocTroChuyen;

@Entity
@Table(name = "nguoi_dung")
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
    @OneToMany(mappedBy = "nguoiDung",fetch = FetchType.EAGER)
    private Set<NguoiDungVaiTro> nguoiDungVaiTro;

    public NguoiDung(Long nguoiDungId, String tenDangNhap, String email, String soDienThoai, String matKhauMaHoa,
            String hoTen, Boolean trangThaiHoatDong, OffsetDateTime taoLuc, OffsetDateTime capNhatLuc,
            Set<NguoiDungVaiTro> nguoiDungVaiTro) {
        this.nguoiDungId = nguoiDungId;
        this.tenDangNhap = tenDangNhap;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.matKhauMaHoa = matKhauMaHoa;
        this.hoTen = hoTen;
        this.trangThaiHoatDong = trangThaiHoatDong;
        this.taoLuc = taoLuc;
        this.capNhatLuc = capNhatLuc;
        this.nguoiDungVaiTro = nguoiDungVaiTro;

    }
    public NguoiDung() {
    }
    public Long getNguoiDungId() {
        return nguoiDungId;
    }
    public void setNguoiDungId(Long nguoiDungId) {
        this.nguoiDungId = nguoiDungId;
    }
    public String getTenDangNhap() {
        return tenDangNhap;
    }
    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSoDienThoai() {
        return soDienThoai;
    }
    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
    public String getMatKhauMaHoa() {
        return matKhauMaHoa;
    }
    public void setMatKhauMaHoa(String matKhauMaHoa) {
        this.matKhauMaHoa = matKhauMaHoa;
    }
    public String getHoTen() {
        return hoTen;
    }
    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }
    public Boolean getTrangThaiHoatDong() {
        return trangThaiHoatDong;
    }
    public void setTrangThaiHoatDong(Boolean trangThaiHoatDong) {
        this.trangThaiHoatDong = trangThaiHoatDong;
    }
    public OffsetDateTime getTaoLuc() {
        return taoLuc;
    }
    public void setTaoLuc(OffsetDateTime taoLuc) {
        this.taoLuc = taoLuc;
    }
    public OffsetDateTime getCapNhatLuc() {
        return capNhatLuc;
    }
    public void setCapNhatLuc(OffsetDateTime capNhatLuc) {
        this.capNhatLuc = capNhatLuc;
    }
    public Set<NguoiDungVaiTro> getNguoiDungVaiTro() {
        return nguoiDungVaiTro;
    }
    public void setNguoiDungVaiTro(Set<NguoiDungVaiTro> nguoiDungVaiTro) {
        this.nguoiDungVaiTro = nguoiDungVaiTro;

    }

    
    
}

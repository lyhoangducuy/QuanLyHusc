package com.example.quanlyhusc.dto.nguoidung;

public class SuaNguoiDungDTO {
    private Long nguoiDungId;
    private String hoTen;
    private String tenDangNhap;
    private String email;
    private String soDienThoai;

    // mật khẩu mới (optional)
    private String matKhauMoi;

    private String maVaiTro;
    private Boolean trangThaiHoatDong = true;
    
    public Long getNguoiDungId() {
        return nguoiDungId;
    }
    public void setNguoiDungId(Long nguoiDungId) {
        this.nguoiDungId = nguoiDungId;
    }
    public String getHoTen() {
        return hoTen;
    }
    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
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
    public String getMatKhauMoi() {
        return matKhauMoi;
    }
    public void setMatKhauMoi(String matKhauMoi) {
        this.matKhauMoi = matKhauMoi;
    }
    public String getMaVaiTro() {
        return maVaiTro;
    }
    public void setMaVaiTro(String maVaiTro) {
        this.maVaiTro = maVaiTro;
    }
    public Boolean getTrangThaiHoatDong() {
        return trangThaiHoatDong;
    }
    public void setTrangThaiHoatDong(Boolean trangThaiHoatDong) {
        this.trangThaiHoatDong = trangThaiHoatDong;
    }
    
}

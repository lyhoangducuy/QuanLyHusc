package com.example.quanlyhusc.dto.nguoidung;

public class ThemNguoiDungDTO {
    private String hoTen;
    private String tenDangNhap;
    private String email;
    private String soDienThoai;
    private String matKhau;
    private String maVaiTro; // ví dụ: ADMIN, SINHVIEN
    private Boolean trangThaiHoatDong = true;
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
    public String getMatKhau() {
        return matKhau;
    }
    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
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

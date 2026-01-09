package com.example.quanlyhusc.dto.nguoidung;

public class DangKyDTO {
    private String hoTen;
    private String tenDangNhap;
    private String email;
    private String soDienThoai;
    private String matKhau;
    private String matKhauNhapLai; // ✅ thêm

    // getters/setters
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getTenDangNhap() { return tenDangNhap; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }

    public String getMatKhauNhapLai() { return matKhauNhapLai; }
    public void setMatKhauNhapLai(String matKhauNhapLai) { this.matKhauNhapLai = matKhauNhapLai; }
}

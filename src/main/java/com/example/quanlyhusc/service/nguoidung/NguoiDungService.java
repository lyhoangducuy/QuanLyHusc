package com.example.quanlyhusc.service.nguoidung;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.quanlyhusc.dto.nguoidung.DangKyDTO;
import com.example.quanlyhusc.dto.nguoidung.SuaNguoiDungDTO;
import com.example.quanlyhusc.dto.nguoidung.ThemNguoiDungDTO;
import com.example.quanlyhusc.entity.NguoiDung;
import com.example.quanlyhusc.entity.baiviet.DanhMucBaiViet;

public interface NguoiDungService {
    NguoiDung findByTenDangNhap(String tenDangNhap);

    NguoiDung findById(Long id);

    NguoiDung findByTenDangNhapFetchRole(String username);

    Page<NguoiDung> getALl(int pageNo);

    Page<NguoiDung> search(String keyword, int pageNo);

    void create(ThemNguoiDungDTO themNguoiDungDTO);

    NguoiDung tim(Long id);

    SuaNguoiDungDTO getSua(Long id);

    void delete(Long id);

    void dangKy(DangKyDTO dangKyDTO);

    Boolean check(DangKyDTO dangKyDTO);

    void taoOtpXacMinhVaGuiMail(String email);

    void xacMinhEmailBangOtp(String email, String otp);

    void guiLinkQuenMatKhau(String email, String baseUrl);

    void datLaiMatKhau(String token, String matKhauMoi, String matKhauNhapLai);
    void updateProfile(Long nguoiDungId,String tenDangNhap, String hoTen,String email,String soDienThoai);
    Long dem();
    long demNguoiDungTuanTruoc(int soTuanTruoc) ;
}

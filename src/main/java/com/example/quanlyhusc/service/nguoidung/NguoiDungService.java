package com.example.quanlyhusc.service.nguoidung;

import org.springframework.security.core.userdetails.UserDetails;

import com.example.quanlyhusc.entity.NguoiDung;


public interface NguoiDungService {
    NguoiDung findByTenDangNhap(String tenDangNhap);
    NguoiDung findById(Long id);
    NguoiDung findByTenDangNhapFetchRole(String username);
}

package com.example.quanlyhusc.service.nguoidung;

import com.example.quanlyhusc.entity.NguoiDung;


public interface NguoiDungService {
    NguoiDung findByTenDangNhap(String tenDangNhap);
}

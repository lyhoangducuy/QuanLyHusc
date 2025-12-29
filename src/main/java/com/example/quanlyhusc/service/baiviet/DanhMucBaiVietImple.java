package com.example.quanlyhusc.service.baiviet;

import java.util.List;

import com.example.quanlyhusc.entity.baiviet.DanhMucBaiViet;

public interface DanhMucBaiVietImple {
    Boolean themDanhMucBaiViet(DanhMucBaiViet danhMucBaiViet);
    Boolean suaDanhMucBaiViet(DanhMucBaiViet danhMucBaiViet);
    Boolean xoaDanhMucBaiViet(Long id);
    List<DanhMucBaiViet> findAll();
    DanhMucBaiViet findById(Long id);
}

package com.example.quanlyhusc.service.baiviet;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.quanlyhusc.entity.baiviet.DanhMucBaiViet;

public interface DanhMucBaiVietImple {
    Boolean themDanhMucBaiViet(DanhMucBaiViet danhMucBaiViet);
    Boolean suaDanhMucBaiViet(DanhMucBaiViet danhMucBaiViet);
    Boolean xoaDanhMucBaiViet(Long id);
   Page<DanhMucBaiViet> getAll(int pageNo);
    DanhMucBaiViet findById(Long id);
    Page<DanhMucBaiViet> searchByTenDanhMuc(String keyword,int pageNo);
    List<DanhMucBaiViet> findAll();
}

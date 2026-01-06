package com.example.quanlyhusc.service.phananh;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.quanlyhusc.entity.phananh.LoaiPhanAnh;

public interface LoaiPhanAnhService {
    Page<LoaiPhanAnh> getAllLoaiPhanAnh(int pageNo);
    Page<LoaiPhanAnh> searchByTenLoai(String keyword, int pageNo);
    LoaiPhanAnh findById(Long id);
    LoaiPhanAnh save(LoaiPhanAnh loaiPhanAnh);
    Boolean deleteById(Long id);
    List<LoaiPhanAnh> getAll();
    Boolean update(LoaiPhanAnh loaiPhanAnh);
}

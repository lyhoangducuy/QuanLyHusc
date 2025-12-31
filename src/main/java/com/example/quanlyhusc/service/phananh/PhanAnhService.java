package com.example.quanlyhusc.service.phananh;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.quanlyhusc.entity.phananh.PhanAnh;

public interface PhanAnhService {
    Page<PhanAnh> getAllPhanAnh(int pageNo);
    PhanAnh findById(Long id);
    Boolean create(PhanAnh phanAnh);
    Boolean updateTrangThai(Long id, String trangThai);
    Page<PhanAnh> searchByTieuDe(String keyword, int pageNo);
}

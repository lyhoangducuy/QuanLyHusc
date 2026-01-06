package com.example.quanlyhusc.repository.phananh;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quanlyhusc.entity.phananh.LoaiPhanAnh;

public interface LoaiPhanAnhRepository extends JpaRepository<LoaiPhanAnh, Long> {
    Page<LoaiPhanAnh> findByTenLoaiContainingIgnoreCase(String keyword, Pageable pageable);
}

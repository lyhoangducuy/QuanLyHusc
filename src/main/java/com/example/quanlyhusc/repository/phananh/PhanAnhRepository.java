package com.example.quanlyhusc.repository.phananh;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quanlyhusc.entity.phananh.PhanAnh;

public interface PhanAnhRepository extends JpaRepository<PhanAnh, Long> {
    Page<PhanAnh> findByTieuDeContainingIgnoreCase(String tieuDe, Pageable pageable);
}

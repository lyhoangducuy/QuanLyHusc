package com.example.quanlyhusc.repository.ungho;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quanlyhusc.entity.ungho.UngHo;


public interface UngHoRepository extends JpaRepository<UngHo, Long> {
    Page<UngHo> findByTieuDeContainingIgnoreCase(String keyword, Pageable pageable);
    Long  countByTrangThai(Boolean trangThai);
}

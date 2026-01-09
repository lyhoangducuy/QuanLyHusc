package com.example.quanlyhusc.repository.ungho;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.quanlyhusc.entity.ungho.GiaoDichUngHo;

public interface GiaoDichUngHoRepository extends JpaRepository<GiaoDichUngHo, Long> {

    Optional<GiaoDichUngHo> findByVnpTxnRef(String vnpTxnRef);

    Optional<GiaoDichUngHo> findByMaDonHang(String maDonHang);

    Page<GiaoDichUngHo> findByUngHo_UngHoIdOrderByTaoLucDesc(Long ungHoId, Pageable pageable);

   
    Page<GiaoDichUngHo> findByUngHo_UngHoIdAndTrangThaiOrderByTaoLucDesc(Long ungHoId, String trangThai,
            Pageable pageable);

    long countByUngHo_UngHoIdAndTrangThai(Long ungHoId, String trangThai);

    // tổng tiền SUCCESS để admin nhìn nhanh
    @Query("""
                select coalesce(sum(g.soTienVnd), 0)
                from GiaoDichUngHo g
                where g.ungHo.ungHoId = :ungHoId and g.trangThai = 'SUCCESS'
            """)
    Long sumSuccessByUngHoId(@Param("ungHoId") Long ungHoId);
}

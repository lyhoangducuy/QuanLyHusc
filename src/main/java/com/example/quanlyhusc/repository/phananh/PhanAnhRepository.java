package com.example.quanlyhusc.repository.phananh;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quanlyhusc.entity.phananh.PhanAnh;
import com.example.quanlyhusc.entity.NguoiDung;




public interface PhanAnhRepository extends JpaRepository<PhanAnh, Long> {
    Page<PhanAnh> findByTieuDeContainingIgnoreCase(String tieuDe, Pageable pageable);
    @EntityGraph(attributePaths = {"loai", "nguoiGui","tepDinhKem", "binhLuans","binhLuans.tacGia","binhLuans.tacGia.nguoiDungVaiTro.vaiTro","binhLuans.tepDinhKemBinhLuans"})
    PhanAnh findByPhanAnhId(Long phanAnhId);

    
    Page<PhanAnh> findByNguoiGui(NguoiDung nguoiGui,Pageable pageable);
    Page<PhanAnh> findByNguoiGuiAndTieuDeContainingIgnoreCase(
    NguoiDung nguoiDung,
    String tieuDe,
    Pageable pageable
);

}

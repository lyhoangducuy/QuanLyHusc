package com.example.quanlyhusc.repository.baiviet;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.quanlyhusc.entity.baiviet.DanhMucBaiViet;

public interface DanhMucBaiVietRepository extends JpaRepository<DanhMucBaiViet, Long>  {
    @Query("""
            SELECT dmbv FROM DanhMucBaiViet dmbv
            WHERE dmbv.tenDanhMuc LIKE %:keyword%
            """)
    List<DanhMucBaiViet> searchByTenDanhMuc(String keyword);    
    Page<DanhMucBaiViet> findByTenDanhMucContainingIgnoreCase(String keyword,Pageable pageable);
    Page<DanhMucBaiViet> findAll(Pageable pageable);

}

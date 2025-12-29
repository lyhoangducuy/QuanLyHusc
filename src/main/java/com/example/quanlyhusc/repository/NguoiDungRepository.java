package com.example.quanlyhusc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quanlyhusc.entity.NguoiDung;
import java.util.List;



public interface NguoiDungRepository extends JpaRepository<NguoiDung,Long>{
    NguoiDung findByTenDangNhap(String tenDangNhap);
    NguoiDung findByNguoiDungId(Long nguoiDungId);
    
}

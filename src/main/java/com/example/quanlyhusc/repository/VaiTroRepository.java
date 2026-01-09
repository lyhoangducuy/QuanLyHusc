package com.example.quanlyhusc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quanlyhusc.entity.NguoiDung;
import com.example.quanlyhusc.entity.VaiTro;
import java.util.List;
import com.example.quanlyhusc.entity.NguoiDungVaiTro;
import java.util.Set;


public interface VaiTroRepository extends JpaRepository<VaiTro,Long >{ 
     VaiTro findByTenVaiTro(String tenVaiTro);
     Set<VaiTro> findByNguoiDungVaiTro(Set<NguoiDungVaiTro> nguoiDungVaiTro);
     VaiTro findByMaVaiTro(String maVaiTro);
}

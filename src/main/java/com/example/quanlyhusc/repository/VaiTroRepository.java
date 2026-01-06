package com.example.quanlyhusc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quanlyhusc.entity.VaiTro;

public interface VaiTroRepository extends JpaRepository<VaiTro,Long >{ 
     VaiTro findByTenVaiTro(String tenVaiTro);
}

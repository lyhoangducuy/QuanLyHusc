package com.example.quanlyhusc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quanlyhusc.entity.TokenQuenMatKhau;
import java.util.List;
import java.util.Optional;


public interface TokenQuenMatKhauRepository extends JpaRepository<TokenQuenMatKhau,Long>{
    Optional<TokenQuenMatKhau> findByToken(String token);
}

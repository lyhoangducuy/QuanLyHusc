package com.example.quanlyhusc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quanlyhusc.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    // Lấy OTP mới nhất còn hiệu lực (chưa thu hồi) theo email
    Optional<RefreshToken> findTopByNguoiDung_EmailAndThuHoiLucIsNullOrderByTaoLucDesc(String email);

    // (Optional) tìm theo email + otp trực tiếp
    Optional<RefreshToken> findFirstByNguoiDung_EmailAndTokenAndThuHoiLucIsNullOrderByTaoLucDesc(String email, String token);
}

package com.example.quanlyhusc.entity;

import java.time.OffsetDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "token_quen_mat_khau")
@Getter
@Setter
public class TokenQuenMatKhau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long tokenId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoi_dung_id", nullable = false)
    private NguoiDung nguoiDung;

    @Column(name = "token", nullable = false, length = 255, unique = true)
    private String token;

    @Column(name = "het_han_luc", nullable = false)
    private OffsetDateTime hetHanLuc;

    @Column(name = "da_dung_luc")
    private OffsetDateTime daDungLuc;

    @Column(name = "tao_luc", nullable = false)
    private OffsetDateTime taoLuc = OffsetDateTime.now();

    public TokenQuenMatKhau() {}
}

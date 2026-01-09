package com.example.quanlyhusc.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
@Entity
@Table(name = "refresh_token")
@Getter
@Setter
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_id")
    private Long refreshId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoi_dung_id", nullable = false)
    private NguoiDung nguoiDung;

    @Column(name = "token", nullable = false, length = 255, unique = true)
    private String token;

    @Column(name = "het_han_luc", nullable = false)
    private OffsetDateTime hetHanLuc;

    @Column(name = "thu_hoi_luc")
    private OffsetDateTime thuHoiLuc;

    @Column(name = "tao_luc", nullable = false)
    private OffsetDateTime taoLuc = OffsetDateTime.now();

    public RefreshToken() {
    }

    public RefreshToken(Long refreshId, NguoiDung nguoiDung, String token, OffsetDateTime hetHanLuc,
            OffsetDateTime thuHoiLuc, OffsetDateTime taoLuc) {
        this.refreshId = refreshId;
        this.nguoiDung = nguoiDung;
        this.token = token;
        this.hetHanLuc = hetHanLuc;
        this.thuHoiLuc = thuHoiLuc;
        this.taoLuc = taoLuc;
    }
    
    // getter / setter
}


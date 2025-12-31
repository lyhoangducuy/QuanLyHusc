package com.example.quanlyhusc.entity.phananh;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

import com.example.quanlyhusc.entity.NguoiDung;

@Entity
@Table(name = "binh_luan_phan_anh")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BinhLuanPhanAnh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "binh_luan_id")
    private Long binhLuanId;

    @ManyToOne
    @JoinColumn(name = "phan_anh_id", nullable = false)
    private PhanAnh phanAnh;

    @ManyToOne
    @JoinColumn(name = "tac_gia_id", nullable = false)
    private NguoiDung tacGia;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String noiDung;

    @Column(name = "tao_luc")
    private OffsetDateTime taoLuc = OffsetDateTime.now();
}

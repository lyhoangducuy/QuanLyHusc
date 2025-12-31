package com.example.quanlyhusc.entity.phananh;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "tep_dinh_kem_phan_anh")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TepDinhKemPhanAnh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tep_id")
    private Long tepId;

    @ManyToOne
    @JoinColumn(name = "phan_anh_id", nullable = false)
    private PhanAnh phanAnh;

    @Column(name = "duong_dan_url", nullable = false)
    private String duongDanUrl;

    @Column(name = "loai_tep", nullable = false, length = 30)
    private String loaiTep; // IMAGE / VIDEO / FILE

    @Column(name = "ten_tep")
    private String tenTep;

    private Long kichThuoc;

    @Column(name = "tao_luc")
    private OffsetDateTime taoLuc = OffsetDateTime.now();
}

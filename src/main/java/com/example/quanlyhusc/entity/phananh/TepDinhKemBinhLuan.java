package com.example.quanlyhusc.entity.phananh;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "tep_dinh_kem_binh_luan")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TepDinhKemBinhLuan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tep_id")
    private Long tepId;

    @ManyToOne
    @JoinColumn(name = "binh_luan_id", nullable = false)
    private BinhLuanPhanAnh binhLuanPhanAnh;

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

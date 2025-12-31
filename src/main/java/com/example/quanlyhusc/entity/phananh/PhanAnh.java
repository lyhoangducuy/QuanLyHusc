package com.example.quanlyhusc.entity.phananh;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.List;

import com.example.quanlyhusc.entity.NguoiDung;

@Entity
@Table(name = "phan_anh")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhanAnh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phan_anh_id")
    private Long phanAnhId;

    @ManyToOne
    @JoinColumn(name = "nguoi_gui_id", nullable = false)
    private NguoiDung nguoiGui;

    @ManyToOne
    @JoinColumn(name = "loai_id", nullable = false)
    private LoaiPhanAnh loai;

    @Column(nullable = false, length = 300)
    private String tieuDe;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String moTa;

    @Column(name = "dia_chi_mo_ta", length = 500)
    private String diaChiMoTa;

    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai", nullable = false, length = 30)
    private TrangThaiPhanAnh trangThai = TrangThaiPhanAnh.CHO_XAC_NHAN;


    @Column(name = "tao_luc")
    private OffsetDateTime taoLuc = OffsetDateTime.now();

    @Column(name = "cap_nhat_luc")
    private OffsetDateTime capNhatLuc = OffsetDateTime.now();

    /* ========= RELATION ========= */

    @OneToMany(mappedBy = "phanAnh", cascade = CascadeType.ALL)
    private List<TepDinhKemPhanAnh> tepDinhKem;

    @OneToMany(mappedBy = "phanAnh", cascade = CascadeType.ALL)
    private List<BinhLuanPhanAnh> binhLuan;

    @PreUpdate
    public void preUpdate() {
        this.capNhatLuc = java.time.OffsetDateTime.now();
    }


}

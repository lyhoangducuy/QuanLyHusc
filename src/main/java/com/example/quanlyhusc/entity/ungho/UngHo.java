package com.example.quanlyhusc.entity.ungho;

import java.time.OffsetDateTime;
import java.util.Set;

import com.example.quanlyhusc.entity.NguoiDung;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ung_ho")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UngHo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ung_ho_id")
    private Long ungHoId;

    @Column(name = "tieu_de", nullable = false)
    private String tieuDe;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "muc_tieu_vnd", nullable = false)
    private Long mucTieuVnd;

    @Column(name = "da_nhan_vnd", nullable = false)
    private Long daNhanVnd = 0L;

    @Column(name = "trang_thai", nullable = false)
    private Boolean trangThai = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tao_boi")
    private NguoiDung taoBoi;

    @Column(name = "tao_luc")
    private OffsetDateTime taoLuc = OffsetDateTime.now();

    @Column(name = "cap_nhat_luc")
    private OffsetDateTime capNhatLuc = OffsetDateTime.now();

    @OneToMany(mappedBy = "ungHo", fetch = FetchType.LAZY)
    private Set<GiaoDichUngHo> giaoDichs;

    // getter / setter
}

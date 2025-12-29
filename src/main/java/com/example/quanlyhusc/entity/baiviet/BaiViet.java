package com.example.quanlyhusc.entity.baiviet;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import com.example.quanlyhusc.entity.NguoiDung;

import jakarta.persistence.*;

@Entity
@Table(name = "bai_viet")
public class BaiViet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bai_viet_id")
    private Long baiVietId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tac_gia_id", nullable = false)
    private NguoiDung tacGiaId;

    @Column(name = "tieu_de", nullable = false, length = 300)
    private String tieuDe;

    @Column(name = "noi_dung", nullable = false, columnDefinition = "text")
    private String noiDung;

    @Column(name = "ghim", nullable = false)
    private Boolean ghim = false;

    @Column(name = "pham_vi_hien_thi", nullable = false, length = 20)
    private String phamViHienThi = "ALL"; // ALL/STUDENT/MANAGER

    @Column(name = "tao_luc", nullable = false)
    private OffsetDateTime taoLuc = OffsetDateTime.now();

    @Column(name = "cap_nhat_luc", nullable = false)
    private OffsetDateTime capNhatLuc = OffsetDateTime.now();

    @OneToMany(mappedBy = "baiViet", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BaiVietDanhMuc> dsDanhMuc = new HashSet<>();

    @OneToMany(mappedBy = "baiViet", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TepDinhKemBaiViet> dsTep = new HashSet<>();

    public BaiViet() {
    }

    public BaiViet(Long id, NguoiDung tacGia, String tieuDe, String noiDung, Boolean ghim, String phamViHienThi,
            OffsetDateTime taoLuc, OffsetDateTime capNhatLuc, Set<BaiVietDanhMuc> dsDanhMuc,
            Set<TepDinhKemBaiViet> dsTep) {
        this.baiVietId = id;
        this.tacGiaId = tacGia;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.ghim = ghim;
        this.phamViHienThi = phamViHienThi;
        this.taoLuc = taoLuc;
        this.capNhatLuc = capNhatLuc;
        this.dsDanhMuc = dsDanhMuc;
        this.dsTep = dsTep;
    }
    public BaiViet(NguoiDung tacGia, String tieuDe, String noiDung, Boolean ghim, String phamViHienThi,Set<BaiVietDanhMuc> dsDanhMuc,
            Set<TepDinhKemBaiViet> dsTep) {
        this.tacGiaId = tacGia;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.ghim = ghim;
        this.phamViHienThi = phamViHienThi;
        this.dsDanhMuc = dsDanhMuc;
        this.dsTep = dsTep;
    }

    public Long getBaiVietId() {
        return baiVietId;
    }

    public void setBaiVietId(Long baiVietId) {
        this.baiVietId = baiVietId;
    }

    public NguoiDung getTacGiaId() {
        return tacGiaId;
    }

    public void setTacGiaId(NguoiDung tacGiaId) {
        this.tacGiaId = tacGiaId;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public Boolean getGhim() {
        return ghim;
    }

    public void setGhim(Boolean ghim) {
        this.ghim = ghim;
    }

    public String getPhamViHienThi() {
        return phamViHienThi;
    }

    public void setPhamViHienThi(String phamViHienThi) {
        this.phamViHienThi = phamViHienThi;
    }

    public OffsetDateTime getTaoLuc() {
        return taoLuc;
    }

    public void setTaoLuc(OffsetDateTime taoLuc) {
        this.taoLuc = taoLuc;
    }

    public OffsetDateTime getCapNhatLuc() {
        return capNhatLuc;
    }

    public void setCapNhatLuc(OffsetDateTime capNhatLuc) {
        this.capNhatLuc = capNhatLuc;
    }

    public Set<BaiVietDanhMuc> getDsDanhMuc() {
        return dsDanhMuc;
    }

    public void setDsDanhMuc(Set<BaiVietDanhMuc> dsDanhMuc) {
        this.dsDanhMuc = dsDanhMuc;
    }

    public Set<TepDinhKemBaiViet> getDsTep() {
        return dsTep;
    }

    public void setDsTep(Set<TepDinhKemBaiViet> dsTep) {
        this.dsTep = dsTep;
    }
    

}

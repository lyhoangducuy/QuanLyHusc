package com.example.quanlyhusc.entity.baiviet;
import java.time.OffsetDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "tep_dinh_kem_bai_viet")
public class TepDinhKemBaiViet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tep_id")
    private Long tepDinhKemBaiVietId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bai_viet_id", nullable = false)
    private BaiViet baiViet;

    @Column(name = "duong_dan_url", nullable = false)
    private String duongDanUrl;

    @Column(name = "loai_tep", nullable = false, length = 30)
    private String loaiTep; // IMAGE/VIDEO/FILE

    @Column(name = "ten_tep", length = 255)
    private String tenTep;

    @Column(name = "kich_thuoc")
    private Long kichThuoc;

    @Column(name = "tao_luc", nullable = false)
    private OffsetDateTime taoLuc = OffsetDateTime.now();

    public TepDinhKemBaiViet() {
    }

    public TepDinhKemBaiViet(Long id, BaiViet baiViet, String duongDanUrl, String loaiTep, String tenTep,
            Long kichThuoc, OffsetDateTime taoLuc) {
        this.tepDinhKemBaiVietId = id;
        this.baiViet = baiViet;
        this.duongDanUrl = duongDanUrl;
        this.loaiTep = loaiTep;
        this.tenTep = tenTep;
        this.kichThuoc = kichThuoc;
        this.taoLuc = taoLuc;
    }

    public Long getId() {
        return tepDinhKemBaiVietId;
    }

    public void setId(Long id) {
        this.tepDinhKemBaiVietId = id;
    }

    public BaiViet getBaiViet() {
        return baiViet;
    }

    public void setBaiViet(BaiViet baiViet) {
        this.baiViet = baiViet;
    }

    public String getDuongDanUrl() {
        return duongDanUrl;
    }

    public void setDuongDanUrl(String duongDanUrl) {
        this.duongDanUrl = duongDanUrl;
    }

    public String getLoaiTep() {
        return loaiTep;
    }

    public void setLoaiTep(String loaiTep) {
        this.loaiTep = loaiTep;
    }

    public String getTenTep() {
        return tenTep;
    }

    public void setTenTep(String tenTep) {
        this.tenTep = tenTep;
    }

    public Long getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(Long kichThuoc) {
        this.kichThuoc = kichThuoc;
    }

    public OffsetDateTime getTaoLuc() {
        return taoLuc;
    }

    public void setTaoLuc(OffsetDateTime taoLuc) {
        this.taoLuc = taoLuc;
    }
    
    
}

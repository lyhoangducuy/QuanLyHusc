package com.example.quanlyhusc.entity.baiviet;
import jakarta.persistence.*;

@Entity
@Table(name = "bai_viet_danh_muc")
public class BaiVietDanhMuc {
    @EmbeddedId
    private BaiVietDanhMucId baiVietDanhMucId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("baiVietId")
    @JoinColumn(name = "bai_viet_id")
    private BaiViet baiViet;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("danhMucId")
    @JoinColumn(name = "danh_muc_id")
    private DanhMucBaiViet danhMuc;

    public BaiVietDanhMuc() {
    }

    public BaiVietDanhMuc(BaiVietDanhMucId id, BaiViet baiViet, DanhMucBaiViet danhMuc) {
        this.baiVietDanhMucId = id;
        this.baiViet = baiViet;
        this.danhMuc = danhMuc;
    }

    public BaiVietDanhMucId getId() {
        return baiVietDanhMucId;
    }

    public void setId(BaiVietDanhMucId id) {
        this.baiVietDanhMucId = id;
    }

    public BaiViet getBaiViet() {
        return baiViet;
    }

    public void setBaiViet(BaiViet baiViet) {
        this.baiViet = baiViet;
    }

    public DanhMucBaiViet getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(DanhMucBaiViet danhMuc) {
        this.danhMuc = danhMuc;
    }

    
}

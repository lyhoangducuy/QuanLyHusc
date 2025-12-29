package com.example.quanlyhusc.entity.baiviet;

import java.io.Serializable;

import jakarta.persistence.*;

@Embeddable
class BaiVietDanhMucId implements Serializable {
    @Column(name = "bai_viet_id")
    private Long baiVietId;
    @Column(name = "danh_muc_id")
    private Long danhMucId;
    public BaiVietDanhMucId() {
    }
    public BaiVietDanhMucId(Long baiVietId, Long danhMucId) {
        this.baiVietId = baiVietId;
        this.danhMucId = danhMucId;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((baiVietId == null) ? 0 : baiVietId.hashCode());
        result = prime * result + ((danhMucId == null) ? 0 : danhMucId.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BaiVietDanhMucId other = (BaiVietDanhMucId) obj;
        if (baiVietId == null) {
            if (other.baiVietId != null)
                return false;
        } else if (!baiVietId.equals(other.baiVietId))
            return false;
        if (danhMucId == null) {
            if (other.danhMucId != null)
                return false;
        } else if (!danhMucId.equals(other.danhMucId))
            return false;
        return true;
    }
    public Long getBaiVietId() {
        return baiVietId;
    }
    public void setBaiVietId(Long baiVietId) {
        this.baiVietId = baiVietId;
    }
    public Long getDanhMucId() {
        return danhMucId;
    }
    public void setDanhMucId(Long danhMucId) {
        this.danhMucId = danhMucId;
    }
    
    

}
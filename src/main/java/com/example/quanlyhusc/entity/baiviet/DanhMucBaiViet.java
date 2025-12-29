package com.example.quanlyhusc.entity.baiviet;

import jakarta.persistence.*;
@Entity
@Table(name = "danh_muc_bai_viet")
public class DanhMucBaiViet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "danh_muc_id")
    private Long danhMucId;

    @Column(name = "ten_danh_muc", nullable = false, unique = true, length = 150)
    private String tenDanhMuc;

    public DanhMucBaiViet(Long id, String tenDanhMuc) {
        this.danhMucId = id;
        this.tenDanhMuc = tenDanhMuc;
    }
    public DanhMucBaiViet(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }
    public DanhMucBaiViet() {
    }

    public Long getDanhMucId() {
        return danhMucId;
    }

    public void setDanhMucId(Long id) {
        this.danhMucId = id;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }
    
}

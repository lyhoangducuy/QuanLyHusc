package com.example.quanlyhusc.dto.baiviet;

import java.util.HashSet;
import java.util.Set;

import com.example.quanlyhusc.entity.baiviet.TepDinhKemBaiViet;

public class BaiVietDTO {
    private Long tacGiaId;
    private String tieuDe;
    private String noiDung;
    private boolean ghim;
    private Set<TepDinhKemBaiViet> dsTep = new HashSet<>();
    public BaiVietDTO() {
    }
    public BaiVietDTO(Long tacGiaId,String tieuDe, String noiDung, boolean ghim, Set<TepDinhKemBaiViet> dsTep) {
        this.tacGiaId = tacGiaId;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.ghim = ghim;
        this.dsTep = dsTep;
    }
    public Long getTacGiaId() {
        return tacGiaId;
    }
    public void setTacGiaId(Long tacGiaId) {
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
    public boolean isGhim() {
        return ghim;
    }
    public void setGhim(boolean ghim) {
        this.ghim = ghim;
    }
    public Set<TepDinhKemBaiViet> getDsTep() {
        return dsTep;
    }
    public void setDsTep(Set<TepDinhKemBaiViet> dsTep) {
        this.dsTep = dsTep;
    }
    
    
}

package com.example.quanlyhusc.dto.phananh;

import java.util.List;

import com.example.quanlyhusc.entity.phananh.TepDinhKemPhanAnh;

public class PhanAnhDTO {
    private String tieuDe;
    private String moTa;
    private Long loaiPhanAnhId;
    private String diaChiMoTa;
    private List<TepDinhKemPhanAnh> dsTepDinhKem;
    private Long nguoiGui;
    public PhanAnhDTO() {
    }
    public PhanAnhDTO(String tieuDe, String moTa, Long loaiPhanAnhId, String diaChiMoTa,
            List<TepDinhKemPhanAnh> dsTepDinhKem, Long nguoiGui) {
        this.tieuDe = tieuDe;
        this.moTa = moTa;
        this.loaiPhanAnhId = loaiPhanAnhId;
        this.diaChiMoTa = diaChiMoTa;
        this.dsTepDinhKem = dsTepDinhKem;
        this.nguoiGui = nguoiGui;
    }
    public String getTieuDe() {
        return tieuDe;
    }
    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }
    public String getMoTa() {
        return moTa;
    }
    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
    public Long getLoaiPhanAnhId() {
        return loaiPhanAnhId;
    }
    public void setLoaiPhanAnhId(Long loaiPhanAnhId) {
        this.loaiPhanAnhId = loaiPhanAnhId;
    }
    public String getDiaChiMoTa() {
        return diaChiMoTa;
    }
    public void setDiaChiMoTa(String diaChiMoTa) {
        this.diaChiMoTa = diaChiMoTa;
    }
    public List<TepDinhKemPhanAnh> getDsTepDinhKem() {
        return dsTepDinhKem;
    }
    public void setDsTepDinhKem(List<TepDinhKemPhanAnh> dsTepDinhKem) {
        this.dsTepDinhKem = dsTepDinhKem;
    }
    public Long getNguoiGui() {
        return nguoiGui;
    }
    public void setNguoiGui(Long nguoiGui) {
        this.nguoiGui = nguoiGui;
    }
    

}

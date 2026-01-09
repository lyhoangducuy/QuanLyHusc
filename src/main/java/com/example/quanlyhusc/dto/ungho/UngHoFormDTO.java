package com.example.quanlyhusc.dto.ungho;

public class UngHoFormDTO {
    private Long ungHoId;
    private String tieuDe;
    private String moTa;
    private Long mucTieuVnd;
    private Boolean trangThai = true;

    public Long getUngHoId() { return ungHoId; }
    public void setUngHoId(Long ungHoId) { this.ungHoId = ungHoId; }

    public String getTieuDe() { return tieuDe; }
    public void setTieuDe(String tieuDe) { this.tieuDe = tieuDe; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public Long getMucTieuVnd() { return mucTieuVnd; }
    public void setMucTieuVnd(Long mucTieuVnd) { this.mucTieuVnd = mucTieuVnd; }

    public Boolean getTrangThai() { return trangThai; }
    public void setTrangThai(Boolean trangThai) { this.trangThai = trangThai; }
}

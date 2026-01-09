package com.example.quanlyhusc.entity;
import jakarta.persistence.*;


@Entity
@Table(name = "nguoi_dung_vai_tro")
public class NguoiDungVaiTro {
    @EmbeddedId
    private NguoiDungVaiTroId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("nguoiDungId")
    @JoinColumn(name="nguoi_dung_id")
    
    private NguoiDung nguoiDung;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("vaiTroId")
    @JoinColumn(name="vai_tro_id")
    private VaiTro vaiTro;

    
   

    public NguoiDungVaiTro() {
    }

    public NguoiDung getNguoiDung() {
        return nguoiDung;
    }

    public NguoiDungVaiTro(NguoiDungVaiTroId id, NguoiDung nguoiDung, VaiTro vaiTro) {
        this.id = id;
        this.nguoiDung = nguoiDung;
        this.vaiTro = vaiTro;
    }

    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }

    public VaiTro getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(VaiTro vaiTro) {
        this.vaiTro = vaiTro;
    }

    public NguoiDungVaiTroId getId() {
        return id;
    }

    public void setId(NguoiDungVaiTroId id) {
        this.id = id;
    }
    
}

package com.example.quanlyhusc.entity;

import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "vai_tro")
public class VaiTro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vai_tro_id")
    private Long vaiTroId;

    @Column(name = "ma_vai_tro", nullable = false, unique = true, length = 30)
    private String maVaiTro; // STUDENT, MANAGER

    @Column(name = "ten_vai_tro", nullable = false, length = 100)
    private String tenVaiTro; // "Sinh viên", "Quản lý"

    @OneToMany(mappedBy = "vaiTro",fetch = FetchType.EAGER)
    private Set<NguoiDungVaiTro> nguoiDungVaiTro;

    public VaiTro() {
    }

    public VaiTro(Long vaiTroId, String maVaiTro, String tenVaiTro, Set<NguoiDungVaiTro> nguoiDungVaiTro) {
        this.vaiTroId = vaiTroId;
        this.maVaiTro = maVaiTro;
        this.tenVaiTro = tenVaiTro;
        this.nguoiDungVaiTro = nguoiDungVaiTro;
    }

    public Long getVaiTroId() {
        return vaiTroId;
    }

    public void setVaiTroId(Long vaiTroId) {
        this.vaiTroId = vaiTroId;
    }

    public String getMaVaiTro() {
        return maVaiTro;
    }

    public void setMaVaiTro(String maVaiTro) {
        this.maVaiTro = maVaiTro;
    }

    public String getTenVaiTro() {
        return tenVaiTro;
    }

    public void setTenVaiTro(String tenVaiTro) {
        this.tenVaiTro = tenVaiTro;
    }

    public Set<NguoiDungVaiTro> getNguoiDungVaiTro() {
        return nguoiDungVaiTro;
    }

    public void setNguoiDungVaiTro(Set<NguoiDungVaiTro> nguoiDungVaiTro) {
        this.nguoiDungVaiTro = nguoiDungVaiTro;
    }
    
    
}
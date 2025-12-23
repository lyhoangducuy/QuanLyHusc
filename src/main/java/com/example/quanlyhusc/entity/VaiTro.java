package com.example.quanlyhusc.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vai_tro")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VaiTro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vai_tro_id")
    private Long vaiTroId;

    @Column(name = "ma_vai_tro", nullable = false, unique = true, length = 30)
    private String maVaiTro; // STUDENT, MANAGER

    @Column(name = "ten_vai_tro", nullable = false, length = 100)
    private String tenVaiTro; // "Sinh viên", "Quản lý"
}
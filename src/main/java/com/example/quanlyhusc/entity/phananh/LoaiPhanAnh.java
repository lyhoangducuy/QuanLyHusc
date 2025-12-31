package com.example.quanlyhusc.entity.phananh;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "loai_phan_anh")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoaiPhanAnh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loai_id")
    private Long loaiId;

    @Column(name = "ma_loai", nullable = false, unique = true, length = 50)
    private String maLoai;

    @Column(name = "ten_loai", nullable = false, length = 150)
    private String tenLoai;
}

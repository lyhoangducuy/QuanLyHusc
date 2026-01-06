package com.example.quanlyhusc.dto.phananh;

import java.util.HashSet;
import java.util.Set;

import com.example.quanlyhusc.entity.phananh.TepDinhKemBinhLuan;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BinhLuanPhanAnhDTO {
    private Long phanAnhId;
    private Long tacGiaId;
    private String noiDung;
    private Set<TepDinhKemBinhLuan> tepDinhKemBinhLuans=new HashSet<>();
}

package com.example.quanlyhusc.entity;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class NguoiDungVaiTroId implements Serializable {
    @Column(name = "nguoi_dung_id")
    
    private Long nguoiDungId;

    @Column(name = "vai_tro_id")
    private Long vaiTroId;
}

package com.example.quanlyhusc.entity.chat;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ThanhVienTroChuyenId implements Serializable {

  @Column(name = "cuoc_tro_chuyen_id")
  private Long cuocTroChuyenId;

  @Column(name = "nguoi_dung_id")
  private Long nguoiDungId;
}

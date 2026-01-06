package com.example.quanlyhusc.entity.chat;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class DaDocTinNhanId implements Serializable {

  @Column(name = "tin_nhan_id")
  private Long tinNhanId;

  @Column(name = "nguoi_dung_id")
  private Long nguoiDungId;
}

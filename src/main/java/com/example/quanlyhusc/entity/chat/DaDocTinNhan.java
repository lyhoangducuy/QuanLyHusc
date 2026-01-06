package com.example.quanlyhusc.entity.chat;

import com.example.quanlyhusc.entity.NguoiDung;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter @Setter
@Entity
@Table(name = "da_doc_tin_nhan")
public class DaDocTinNhan {

  @EmbeddedId
  private DaDocTinNhanId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("tinNhanId")
  @JoinColumn(name = "tin_nhan_id")
  private TinNhan tinNhan;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("nguoiDungId")
  @JoinColumn(name = "nguoi_dung_id")
  private NguoiDung nguoiDung;

  @Column(name = "doc_luc", nullable = false)
  private OffsetDateTime docLuc;

  @PrePersist
  public void ganThoiGianDoc() {
    docLuc = OffsetDateTime.now();
  }
}

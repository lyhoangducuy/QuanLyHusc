package com.example.quanlyhusc.entity.chat;

import com.example.quanlyhusc.entity.NguoiDung;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter @Setter
@Entity
@Table(name = "thanh_vien_tro_chuyen")
public class ThanhVienTroChuyen {

  @EmbeddedId
  private ThanhVienTroChuyenId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("cuocTroChuyenId")
  @JoinColumn(name = "cuoc_tro_chuyen_id")
  private CuocTroChuyen cuocTroChuyen;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("nguoiDungId")
  @JoinColumn(name = "nguoi_dung_id")
  private NguoiDung nguoiDung;

  @Column(name = "vai_tro_trong_chat")
  private String vaiTroTrongChat = "MEMBER";

  @Column(name = "tham_gia_luc", nullable = false)
  private OffsetDateTime thamGiaLuc;

  @PrePersist
  public void ganThoiGianThamGia() {
    thamGiaLuc = OffsetDateTime.now();
  }
}

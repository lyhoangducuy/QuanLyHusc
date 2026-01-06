package com.example.quanlyhusc.entity.chat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter @Setter
@Entity
@Table(name = "tep_dinh_kem_tin_nhan")
public class TepDinhKemTinNhan {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "tep_id")
  private Long tepId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tin_nhan_id", nullable = false)
  private TinNhan tinNhan;

  @Column(name = "duong_dan_url", nullable = false, columnDefinition = "TEXT")
  private String duongDanUrl;

  @Column(name = "ten_tep")
  private String tenTep;

  @Column(name = "mime_type")
  private String mimeType;

  @Column(name = "kich_thuoc")
  private Long kichThuoc;

  @Column(name = "tao_luc", nullable = false)
  private OffsetDateTime taoLuc;

  @PrePersist
  public void ganThoiGianTao() {
    taoLuc = OffsetDateTime.now();
  }
}

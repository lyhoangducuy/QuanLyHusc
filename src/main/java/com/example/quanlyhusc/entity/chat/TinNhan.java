package com.example.quanlyhusc.entity.chat;

import com.example.quanlyhusc.entity.NguoiDung;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter @Setter
@Entity
@Table(name = "tin_nhan")
public class TinNhan {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "tin_nhan_id")
  private Long tinNhanId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cuoc_tro_chuyen_id", nullable = false)
  private CuocTroChuyen cuocTroChuyen;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "nguoi_gui_id", nullable = false)
  private NguoiDung nguoiGui;

  @Column(name = "noi_dung", columnDefinition = "TEXT")
  private String noiDung;

  @Column(name = "loai_tin_nhan", nullable = false)
  private String loaiTinNhan = "TEXT"; // TEXT/IMAGE/VIDEO/FILE/SYSTEM

  @Column(name = "tao_luc", nullable = false)
  private OffsetDateTime taoLuc;

  @PrePersist
  public void ganThoiGianTao() {
    taoLuc = OffsetDateTime.now();
  }
}

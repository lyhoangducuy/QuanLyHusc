package com.example.quanlyhusc.entity.chat;

import com.example.quanlyhusc.entity.NguoiDung;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter @Setter
@Entity
@Table(name = "cuoc_tro_chuyen")
public class CuocTroChuyen {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cuoc_tro_chuyen_id")
  private Long cuocTroChuyenId;

  @Column(name = "loai", nullable = false)
  private String loai = "DIRECT"; // DIRECT/GROUP

  @Column(name = "tieu_de")
  private String tieuDe;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tao_boi_id")
  private NguoiDung taoBoi;

  @Column(name = "tao_luc", nullable = false)
  private OffsetDateTime taoLuc;

  @Column(name = "cap_nhat_luc", nullable = false)
  private OffsetDateTime capNhatLuc;

  @PrePersist
  public void ganThoiGianTao() {
    OffsetDateTime now = OffsetDateTime.now();
    taoLuc = now;
    capNhatLuc = now;
  }

  @PreUpdate
  public void ganThoiGianCapNhat() {
    capNhatLuc = OffsetDateTime.now();
  }
}

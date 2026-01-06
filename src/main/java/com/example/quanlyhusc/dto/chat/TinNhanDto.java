package com.example.quanlyhusc.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TinNhanDto {
  private Long tinNhanId;
  private Long cuocTroChuyenId;

  private Long nguoiGuiId;
  private String tenNguoiGui;

  private String noiDung;
  private String loai;
  private String taoLuc; // chuỗi ISO
}

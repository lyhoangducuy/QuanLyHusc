package com.example.quanlyhusc.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class YeuCauGuiTinNhan {
  private Long cuocTroChuyenId;
  private String noiDung;
  private String loai; // TEXT/IMAGE/VIDEO/FILE
}

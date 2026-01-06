package com.example.quanlyhusc.repository.chat;

import com.example.quanlyhusc.entity.chat.ThanhVienTroChuyen;
import com.example.quanlyhusc.entity.chat.ThanhVienTroChuyenId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThanhVienTroChuyenRepository extends JpaRepository<ThanhVienTroChuyen, ThanhVienTroChuyenId> {
  boolean existsById_CuocTroChuyenIdAndId_NguoiDungId(Long cuocId, Long nguoiDungId);
  boolean existsByCuocTroChuyen_CuocTroChuyenIdAndNguoiDung_NguoiDungId(Long cuocId, Long nguoiDungId);
}

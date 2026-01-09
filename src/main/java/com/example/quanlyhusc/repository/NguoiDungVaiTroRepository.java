package com.example.quanlyhusc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.quanlyhusc.entity.NguoiDungVaiTro;
import com.example.quanlyhusc.entity.NguoiDungVaiTroId;

public interface NguoiDungVaiTroRepository extends JpaRepository<NguoiDungVaiTro, NguoiDungVaiTroId> {
    @Modifying
@Query("delete from NguoiDungVaiTro ndvt where ndvt.nguoiDung.nguoiDungId = :id")
void deleteByNguoiDungId(@Param("id") Long id);
}

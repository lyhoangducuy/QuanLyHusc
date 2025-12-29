package com.example.quanlyhusc.repository.baiviet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quanlyhusc.entity.baiviet.BaiViet;
import com.example.quanlyhusc.entity.baiviet.DanhMucBaiViet;

public interface DanhMucBaiVietRepository extends JpaRepository<DanhMucBaiViet, Long>  {
}

package com.example.quanlyhusc.repository.baiviet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import com.example.quanlyhusc.entity.baiviet.TepDinhKemBaiViet;

public interface TepDinhKemBaiVietRepository  extends JpaRepository<TepDinhKemBaiViet,Long>{
    List<TepDinhKemBaiViet> findByIdInAndBaiViet_BaiVietId(List<Long> dsTepXoa,Long id);
}

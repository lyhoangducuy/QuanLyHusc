package com.example.quanlyhusc.repository.phananh;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quanlyhusc.entity.phananh.TepDinhKemPhanAnh;

public interface TepDinhKemPhanAnhRepository extends JpaRepository<TepDinhKemPhanAnh,Long> {
    void deleteByDuongDanUrl(String duongDanUrl);
    List<TepDinhKemPhanAnh> findByTepIdInAndPhanAnh_PhanAnhId(List<Long> tepIds, Long phanAnhId);
}

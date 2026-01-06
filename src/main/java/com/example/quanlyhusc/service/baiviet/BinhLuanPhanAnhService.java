package com.example.quanlyhusc.service.baiviet;

import org.springframework.web.multipart.MultipartFile;

import com.example.quanlyhusc.dto.phananh.BinhLuanPhanAnhDTO;
import com.example.quanlyhusc.entity.phananh.BinhLuanPhanAnh;

public interface BinhLuanPhanAnhService {
    Boolean taoBinhLuanPhanAnh(BinhLuanPhanAnhDTO binhLuanPhanAnhDTO,MultipartFile[] files);
    Boolean xoaBinhLuanPhanAnh(Long id);
    Boolean suaBinhLuanPhanAnh(BinhLuanPhanAnh binhLuanPhanAnh);
}

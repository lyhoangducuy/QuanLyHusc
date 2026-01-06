package com.example.quanlyhusc.service.baiviet;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.example.quanlyhusc.dto.baiviet.BaiVietDTO;
import com.example.quanlyhusc.entity.baiviet.BaiViet;
import com.example.quanlyhusc.entity.phananh.PhanAnh;

public interface BaiVietService {
    List<BaiViet> getAll();
    BaiViet findById(Long id);
    Boolean update(Long id, BaiVietDTO baiVietDTO, MultipartFile[] files,List<Long> dsTepXoa);
    
    Boolean create(BaiVietDTO baiVietDTO, MultipartFile[] files);
    Boolean deleteById(Long id);
    BaiViet findByIdFetchDsTep(Long id);
    BaiViet findByGhimIsTrue();
    List<BaiViet> searchByTieuDe(String keyword);
    Page<BaiViet> getAll(int pageNo);
    Page<BaiViet> search(String keyword, int pageNo);
    BaiViet  findByBaiVietId(Long id);
}

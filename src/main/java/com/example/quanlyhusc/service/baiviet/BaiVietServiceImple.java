package com.example.quanlyhusc.service.baiviet;

import java.util.List;

import com.example.quanlyhusc.dto.baiviet.BaiVietDTO;
import com.example.quanlyhusc.entity.baiviet.BaiViet;

public interface BaiVietServiceImple {
    List<BaiViet> getAll();
    BaiViet findById(Long id);
    Boolean update(Long id, BaiVietDTO baiVietDTO);
    
    Boolean create(BaiVietDTO baiVietDTO);
    Boolean deleteById(Long id);
}

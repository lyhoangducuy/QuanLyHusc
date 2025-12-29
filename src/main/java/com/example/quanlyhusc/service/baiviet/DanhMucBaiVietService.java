package com.example.quanlyhusc.service.baiviet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quanlyhusc.entity.baiviet.DanhMucBaiViet;
import com.example.quanlyhusc.repository.baiviet.DanhMucBaiVietRepository;

@Service
public class DanhMucBaiVietService implements DanhMucBaiVietImple {

    @Autowired
    private DanhMucBaiVietRepository danhMucBaiVietRepository;

    @Override
    public Boolean themDanhMucBaiViet(DanhMucBaiViet danhMucBaiViet) {
        this.danhMucBaiVietRepository.save(danhMucBaiViet);
        return true;
        
    }

    @Override
    public Boolean suaDanhMucBaiViet(DanhMucBaiViet danhMucBaiViet) {
        try {
            this.danhMucBaiVietRepository.save(danhMucBaiViet);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean xoaDanhMucBaiViet(Long id) {
       try {
        this.danhMucBaiVietRepository.deleteById(id);
        return true;
       } catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
        return false;
       }
    }
    @Override
    public List<DanhMucBaiViet> findAll() {
       return this.danhMucBaiVietRepository.findAll();
    }

    @Override
    public DanhMucBaiViet findById(Long id) {
        return this.danhMucBaiVietRepository.findById(id).orElse(null);
    }
    
}

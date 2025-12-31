package com.example.quanlyhusc.service.baiviet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public Page<DanhMucBaiViet> getAll(int pageNo) {
        PageRequest pageable = PageRequest.of(pageNo - 1, 6);
       return this.danhMucBaiVietRepository.findAll(pageable);
    }

    @Override
    public DanhMucBaiViet findById(Long id) {
        return this.danhMucBaiVietRepository.findById(id).orElse(null);
    }

    @Override
    public Page<DanhMucBaiViet> searchByTenDanhMuc(String keyword,int pageNo) {
        PageRequest pageable = PageRequest.of(pageNo - 1, 6);
        return this.danhMucBaiVietRepository.findByTenDanhMucContainingIgnoreCase(keyword,pageable);
    }

    @Override
    public List<DanhMucBaiViet> findAll() {
        return this.danhMucBaiVietRepository.findAll();
    }
    
}

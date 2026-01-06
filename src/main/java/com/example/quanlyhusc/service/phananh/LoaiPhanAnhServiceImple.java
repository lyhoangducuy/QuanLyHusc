package com.example.quanlyhusc.service.phananh;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.quanlyhusc.entity.phananh.LoaiPhanAnh;
import com.example.quanlyhusc.repository.phananh.LoaiPhanAnhRepository;

@Service
public class LoaiPhanAnhServiceImple implements LoaiPhanAnhService {

    @Autowired
    private LoaiPhanAnhRepository loaiPhanAnhRepository;
    @Override
    public Page<LoaiPhanAnh> getAllLoaiPhanAnh(int pageNo) {
        PageRequest pageable = PageRequest.of(pageNo - 1, 10);
        return loaiPhanAnhRepository.findAll(pageable);
    }

    @Override
    public Page<LoaiPhanAnh> searchByTenLoai(String keyword, int pageNo) {
        PageRequest pageable = PageRequest.of(pageNo - 1, 10);
        return loaiPhanAnhRepository.findByTenLoaiContainingIgnoreCase(keyword, pageable);
    }

    @Override
    public LoaiPhanAnh findById(Long id) {
        return loaiPhanAnhRepository.findById(id).orElse(null);
    }

    @Override
    public LoaiPhanAnh save(LoaiPhanAnh loaiPhanAnh) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Boolean deleteById(Long id) {
        try {
            loaiPhanAnhRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<LoaiPhanAnh> getAll() {
        return this.loaiPhanAnhRepository.findAll();
    }

    @Override
    public Boolean update(LoaiPhanAnh loaiPhanAnh) {
        try {
            this.loaiPhanAnhRepository.save(loaiPhanAnh);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

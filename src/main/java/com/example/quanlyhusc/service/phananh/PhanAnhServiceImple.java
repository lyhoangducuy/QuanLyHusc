package com.example.quanlyhusc.service.phananh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.quanlyhusc.entity.phananh.PhanAnh;
import com.example.quanlyhusc.repository.phananh.PhanAnhRepository;

@Service
public class PhanAnhServiceImple implements PhanAnhService {
    @Autowired
    private PhanAnhRepository phanAnhRepository;

    @Override
    public Page<PhanAnh> getAllPhanAnh(int pageNo) {
        Pageable pageable = Pageable.ofSize(10).withPage(pageNo - 1);
        return phanAnhRepository.findAll(pageable);
    }

    @Override
    public PhanAnh findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Boolean create(PhanAnh phanAnh) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public Boolean updateTrangThai(Long id, String trangThai) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTrangThai'");
    }

    @Override
    public Page<PhanAnh> searchByTieuDe(String keyword, int pageNo) {
        Pageable pageable = Pageable.ofSize(10).withPage(pageNo - 1);
        return phanAnhRepository.findByTieuDeContainingIgnoreCase(keyword, pageable);
    }
    
}

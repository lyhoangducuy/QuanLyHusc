package com.example.quanlyhusc.service.nguoidung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.quanlyhusc.entity.NguoiDung;
import com.example.quanlyhusc.repository.NguoiDungRepository;

@Service
public class NguoiDungServiceImple implements NguoiDungService{

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Override
    public NguoiDung findByTenDangNhap(String tenDangNhap) {
        return nguoiDungRepository.findByTenDangNhap(tenDangNhap);
    }

    @Override
    public NguoiDung findById(Long id) {
        return nguoiDungRepository.findById(id).orElse(null);   
    }

    @Override
    public NguoiDung findByTenDangNhapFetchRole(String username) {
        return this.nguoiDungRepository.findByTenDangNhapFetchRole( username);
    }
    
}

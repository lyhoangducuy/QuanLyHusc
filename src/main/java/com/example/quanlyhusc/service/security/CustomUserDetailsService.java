package com.example.quanlyhusc.service.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.quanlyhusc.entity.CustomUserDetails;
import com.example.quanlyhusc.entity.NguoiDung;
import com.example.quanlyhusc.entity.NguoiDungVaiTro;
import com.example.quanlyhusc.service.nguoidung.NguoiDungService;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    @Autowired
    private NguoiDungService nguoiDungService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NguoiDung nguoiDung=nguoiDungService.findByTenDangNhap(username);
        if (nguoiDung==null){
            throw new UsernameNotFoundException("Ten nguoi dung khong ton tai");
        }
        Collection<GrantedAuthority> grantedAuthorities=new HashSet<>();
        Set<NguoiDungVaiTro> nguoiDungVaiTros=nguoiDung.getNguoiDungVaiTro();
        for (NguoiDungVaiTro n:nguoiDungVaiTros ){
            grantedAuthorities.add(new SimpleGrantedAuthority(n.getVaiTro().getTenVaiTro()));
        }
        return new CustomUserDetails(nguoiDung, grantedAuthorities);
    }
    
    
}

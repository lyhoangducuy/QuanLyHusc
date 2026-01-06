package com.example.quanlyhusc.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails{
    private NguoiDung nguoiDung; 
    private Collection<? extends GrantedAuthority> authorities;

    
    public CustomUserDetails() {
    }
    
    public CustomUserDetails(NguoiDung nguoiDung, Collection<? extends GrantedAuthority> authorities) {
        this.nguoiDung = nguoiDung;
        this.authorities = authorities;
    }
    public Long getNguoiDungId() {
        return nguoiDung.getNguoiDungId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return authorities;
    }

    @Override
    public String getPassword() {
        return nguoiDung.getMatKhauMaHoa();
    }

    @Override
    public String getUsername() {
        return nguoiDung.getTenDangNhap();
    }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }


    
}

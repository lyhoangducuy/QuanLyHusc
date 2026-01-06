package com.example.quanlyhusc.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class NguoiDungController {
    @GetMapping("/login")
    public String getLogin() {
        return "auth/login";
    }
}

package com.example.quanlyhusc.controller.sinhvien;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class SinhVienController {
    @GetMapping("/TrangChu")
    public String getMethodName() {
        return "sinhvien/trangChu.html";
    }
    
}

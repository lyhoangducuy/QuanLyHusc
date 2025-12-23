package com.example.quanlyhusc.controller.quanly;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class QuanLyController {
    @GetMapping("/")
    public String getMethodName() {
        return "quanly/DashBoardAdmin";
    }
    
}

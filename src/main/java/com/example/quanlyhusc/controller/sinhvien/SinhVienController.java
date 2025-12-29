package com.example.quanlyhusc.controller.sinhvien;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.quanlyhusc.entity.baiviet.BaiViet;
import com.example.quanlyhusc.service.baiviet.BaiVietService;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class SinhVienController {
    @Autowired
    private BaiVietService baiVietService;
    @GetMapping("/")
    public String getTrangChu(Model model) {
        model.addAttribute("dsBaiViet", this.baiVietService.getAll());
        return "sinhvien/trangchu/trangChu";
    }
    @GetMapping("/BaiViet/{id}")
    public String getBaiViet(@PathVariable("id")Long id,Model model) {
        model.addAttribute("baiViet",this.baiVietService.findById(id));
        return "sinhvien/trangchu/chiTietBaiViet";
    }
    
    
}

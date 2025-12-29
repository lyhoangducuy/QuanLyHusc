package com.example.quanlyhusc.controller.baiviet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quanlyhusc.dto.baiviet.BaiVietDTO;
import com.example.quanlyhusc.entity.CustomUserDetails;
import com.example.quanlyhusc.entity.baiviet.BaiViet;
import com.example.quanlyhusc.service.baiviet.BaiVietService;
import com.example.quanlyhusc.service.baiviet.BaiVietServiceImple;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import jakarta.websocket.server.PathParam;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/admin/BaiViet")
public class BaiVietController {

    @Autowired
    private BaiVietService baiVietService;

    @GetMapping("")
    public String getBaiViet(Model model){
        List<BaiViet> ds=this.baiVietService.getAll();
        model.addAttribute("dsBaiViet", ds);
        return "admin/baiviet/baiViet";
    }
    @GetMapping("/them")
    public String getThem(Model model) {
        return "admin/baiviet/themBaiViet";
    }
    @PostMapping("/them")
    public String themBaiViet(@ModelAttribute BaiVietDTO baiVietDTO) {
        baiVietService.create(baiVietDTO);
        return "redirect:/admin/BaiViet";   
    }
    @GetMapping("/xem/{id}")
    public String xemBaiViet(@PathVariable Long id, Model model) {
        BaiViet baiViet = baiVietService.findById(id);
        model.addAttribute("baiViet", baiViet);
        return "admin/baiviet/xemBaiViet";
    }
    @PostMapping("/xoa/{id}")
    public String xoa(@PathVariable Long id) {
    baiVietService.deleteById(id);
    return "redirect:/admin/BaiViet";
    }
    @GetMapping("/sua/{id}")
    public String sua(@PathVariable Long id, Model model) {
        BaiViet baiViet = baiVietService.findById(id);
        model.addAttribute("baiViet", baiViet);
        return "admin/baiviet/suaBaiViet";
    }
    @PostMapping("/sua/{id}")
    public String sua(@PathVariable Long id, @ModelAttribute("baiViet") BaiVietDTO dto) {
        baiVietService.update(id, dto);
        return "redirect:/admin/BaiViet";
    }
    
}

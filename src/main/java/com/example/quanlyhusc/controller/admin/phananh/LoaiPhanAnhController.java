package com.example.quanlyhusc.controller.admin.phananh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.quanlyhusc.entity.phananh.LoaiPhanAnh;
import com.example.quanlyhusc.service.phananh.LoaiPhanAnhService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/admin/LoaiPhanAnh")
public class LoaiPhanAnhController {
    @Autowired
    private LoaiPhanAnhService loaiPhanAnhService;
    
    @GetMapping("")
    public String getLoaiPhanAnh(Model model,
            @RequestParam(name="keyword", required=false) String keyword,
            @RequestParam(name="pageNo", defaultValue="1") int pageNo) {
        Page<LoaiPhanAnh> list=null;
        if (keyword != null && !keyword.isEmpty()) {
            list=this.loaiPhanAnhService.searchByTenLoai(keyword,pageNo);
        } else {
            list=loaiPhanAnhService.getAllLoaiPhanAnh(pageNo);
        }
        model.addAttribute("totalpage", list.getTotalPages());
        model.addAttribute("currentpage", pageNo);
        model.addAttribute("dsLoaiPhanAnh", list.getContent());
        model.addAttribute("keyword", keyword);
        return "admin/phananh/loai/loaiPhanAnh";
    }
    @GetMapping("/them")
    public String getThem() {
        return "admin/phanAnh/loai/themLoai";
    }
    @PostMapping("/them")
    public String postThem(@ModelAttribute LoaiPhanAnh loaiPhanAnh ) {
        this.loaiPhanAnhService.save(loaiPhanAnh);
        return "redirect:/admin/LoaiPhanAnh";
    }
    @GetMapping("/sua/{id}")
    public String getXem(@PathVariable("id") Long id,Model model) {
        model.addAttribute("loaiPhanAnh",this.loaiPhanAnhService.findById(id));
        return "admin/phanAnh/loai/suaLoaiPhanAnh";
    }
    @PostMapping("/sua/{id}")
    public String postXem(@PathVariable("id") Long id,@ModelAttribute LoaiPhanAnh loaiPhanAnh) {
        this.loaiPhanAnhService.update(loaiPhanAnh);
        return "redirect:/admin/LoaiPhanAnh/sua/{id}";
    }
    
    
    
    
}

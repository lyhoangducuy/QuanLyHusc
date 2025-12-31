package com.example.quanlyhusc.controller.admin.baiviet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.quanlyhusc.entity.baiviet.DanhMucBaiViet;
import com.example.quanlyhusc.service.baiviet.DanhMucBaiVietService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/admin/DanhMuc")
public class DanhMucBaiVietController {
    @Autowired
    private DanhMucBaiVietService danhMucBaiVietService;
    
    @GetMapping("")
    public String getMethodName(Model model,@RequestParam(name="keyword", required=false) String keyword,
            @RequestParam(name="pageNo", defaultValue="1") int pageNo) {
        if (keyword != null && !keyword.isEmpty()) {
            Page<DanhMucBaiViet> list=this.danhMucBaiVietService.searchByTenDanhMuc(keyword,pageNo);
            model.addAttribute("dsDanhMuc",list);
            model.addAttribute("totalpage",list.getTotalPages());
            model.addAttribute("currentpage",pageNo);
        } else {
            Page<DanhMucBaiViet> list=this.danhMucBaiVietService.getAll(pageNo);
            model.addAttribute("dsDanhMuc",list);
            model.addAttribute("dsDanhMuc",list);
            model.addAttribute("totalpage",list.getTotalPages());
            model.addAttribute("currentpage",pageNo);
        }
        model.addAttribute("keyword", keyword);
        return "admin/baiviet/danhmuc/danhMuc";
    }
    @GetMapping("/them")
    public String getThem() {
        return "admin/baiviet/danhmuc/themDanhMuc";
    }
    @PostMapping("/them")
    public String postThem(@ModelAttribute DanhMucBaiViet danhMucBaiViet) {
       this.danhMucBaiVietService.themDanhMucBaiViet(danhMucBaiViet);
       return "redirect:/admin/DanhMuc";
    }
    @GetMapping("/sua/{id}")
    public String getSua(@PathVariable("id") Long id, Model model) {
        model.addAttribute("danhMucBaiViet", this.danhMucBaiVietService.findById(id));
        return "admin/baiviet/danhmuc/suaDanhMuc";
    }
    @PostMapping("/sua/{id}")
    public String postSua(@PathVariable("id") Long id, @ModelAttribute DanhMucBaiViet danhMucBaiViet) {
        danhMucBaiViet.setDanhMucId(id);
        this.danhMucBaiVietService.suaDanhMucBaiViet(danhMucBaiViet);
        return "redirect:/admin/DanhMuc";
        
    }
    @PostMapping("/xoa/{id}")
    public String postXoa(@PathVariable("id") Long id) {
        this.danhMucBaiVietService.xoaDanhMucBaiViet(id);
        return "redirect:/admin/DanhMuc";
    }
    
    
    
}

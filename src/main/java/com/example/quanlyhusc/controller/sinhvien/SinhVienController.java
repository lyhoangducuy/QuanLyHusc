package com.example.quanlyhusc.controller.sinhvien;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.quanlyhusc.entity.baiviet.BaiViet;
import com.example.quanlyhusc.service.baiviet.BaiVietService;
import com.example.quanlyhusc.service.baiviet.BaiVietServiceImple;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class SinhVienController {
    @Autowired
    private BaiVietService baiVietService;
    @GetMapping("/")
    public String getTrangChu(Model model,@RequestParam(name="keyword", required=false) String keyword,
            @RequestParam(name="pageNo", defaultValue="1") int pageNo) {
        
        if (keyword != null && !keyword.isEmpty()) {
            Page<BaiViet> list=this.baiVietService.search(keyword,pageNo);
            model.addAttribute("dsBaiViet",list.getContent());
            model.addAttribute("totalpage",list.getTotalPages());
            model.addAttribute("currentpage",pageNo);
            model.addAttribute("baiVietGhim", null);
            model.addAttribute("keyword", keyword);
        } else {
            Page<BaiViet> list=this.baiVietService.getAll(pageNo);
            model.addAttribute("dsBaiViet",list.getContent());
            model.addAttribute("totalpage",list.getTotalPages());
            model.addAttribute("currentpage",pageNo);
            model.addAttribute("baiVietGhim",this.baiVietService.findByGhimIsTrue());
        }
        return "sinhvien/trangchu/trangChu";
    }
    @GetMapping("/BaiViet/{id}")
    public String getBaiViet(@PathVariable("id")Long id,Model model) {
        BaiViet baiViet = baiVietService.findByIdFetchDsTep(id);
        model.addAttribute("baiViet",baiViet);
        return "sinhvien/trangchu/chiTietBaiViet";
    }
    
    
    
}

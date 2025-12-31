package com.example.quanlyhusc.controller.admin.phananh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.quanlyhusc.entity.phananh.PhanAnh;
import com.example.quanlyhusc.service.phananh.PhanAnhService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/admin/PhanAnh")
public class PhanAnhController {
    @Autowired
    private PhanAnhService phanAnhService;

    @GetMapping("")
    public String getPhanAnh(Model model,
            @RequestParam(name="keyword", required=false) String keyword,
            @RequestParam(name="pageNo", defaultValue="1") int pageNo) {
        Page<PhanAnh> list=null;
        if (keyword != null && !keyword.isEmpty()) {
            list=this.phanAnhService.searchByTieuDe(keyword,pageNo);
        } else {
            list=phanAnhService.getAllPhanAnh(pageNo);
        }
        model.addAttribute("totalpage", list.getTotalPages());
        model.addAttribute("currentpage", pageNo);
        model.addAttribute("dsPhanAnh", list.getContent());
        model.addAttribute("keyword", keyword);
        return "admin/phananh/phanAnh";
    }
    
}

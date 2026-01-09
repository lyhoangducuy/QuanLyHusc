package com.example.quanlyhusc.controller.admin.nguoidung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.quanlyhusc.dto.nguoidung.ThemNguoiDungDTO;
import com.example.quanlyhusc.entity.NguoiDung;
import com.example.quanlyhusc.entity.baiviet.BaiViet;
import com.example.quanlyhusc.service.nguoidung.NguoiDungService;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/admin/NguoiDung")
public class NguoiDungAdminController {
    @Autowired
    private NguoiDungService nguoiDungService;

    @GetMapping()
    public String getNguoiDung(Model model, @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "pageNo", defaultValue = "1") int pageNo) {
        Page<NguoiDung> list;

        if (keyword != null && !keyword.trim().isEmpty()) {
            list = this.nguoiDungService.search(keyword.trim(), pageNo);
        } else {
            list = this.nguoiDungService.getALl(pageNo);
        }
        model.addAttribute("dsNguoiDung", list.getContent());
        model.addAttribute("totalpage", list.getTotalPages());
        model.addAttribute("currentpage", pageNo);
        model.addAttribute("keyword", keyword);
        return "admin/nguoidung/nguoiDung";
    }

    @GetMapping("/them")
    public String getThem(Model model) {

        model.addAttribute("form", new ThemNguoiDungDTO());
        return "admin/nguoidung/them";
    }

    @PostMapping("/them")
    public String themSubmit(@ModelAttribute("form") ThemNguoiDungDTO form, Model model) {
        try {
            this.nguoiDungService.create(form);
            return "redirect:/admin/NguoiDung";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "admin/nguoidung/them";
        }
    }

    @GetMapping("/xem/{id}")
    public String getXem(@PathVariable("id") Long id, Model model,
            @RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(name = "keyword", required = false) String keyword) {
        model.addAttribute("u", this.nguoiDungService.tim(id));
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("keyword", keyword);
        return "admin/nguoidung/xem";
    }

    @GetMapping("/sua/{id}")
    public String getSua(@PathVariable("id") Long id, Model model,
            @RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(name = "keyword", required = false) String keyword) {
        model.addAttribute("form", this.nguoiDungService.getSua(id));
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("keyword", keyword);
        return "admin/nguoidung/sua";
    }

    @PostMapping("/xoa/{id}")
    public String xoa(@PathVariable("id") Long id,
            @RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(name = "keyword", required = false) String keyword) {

        this.nguoiDungService.delete(id);

        // quay về đúng trang đang đứng
        String url = "redirect:/admin/NguoiDung?pageNo=" + pageNo;
        if (keyword != null && !keyword.trim().isEmpty()) {
            url += "&keyword=" + keyword.trim();
        }
        return url;
    }

}

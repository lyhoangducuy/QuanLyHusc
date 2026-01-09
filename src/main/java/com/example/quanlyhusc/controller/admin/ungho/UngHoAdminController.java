package com.example.quanlyhusc.controller.admin.ungho;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.quanlyhusc.dto.ungho.UngHoFormDTO;
import com.example.quanlyhusc.entity.ungho.GiaoDichUngHo;
import com.example.quanlyhusc.entity.ungho.UngHo;
import com.example.quanlyhusc.service.ungho.UngHoDonateService;
import com.example.quanlyhusc.service.ungho.UngHoService;

@Controller
@RequestMapping("/admin/UngHo")
public class UngHoAdminController {

    @Autowired
    private UngHoService ungHoService;
    @Autowired
    private UngHoDonateService ungHoDonateService;

    @GetMapping
    public String list(Model model,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "pageNo", defaultValue = "1") int pageNo) {

        Page<UngHo> page = ungHoService.list(keyword, pageNo, 6);

        model.addAttribute("dsUngHo", page.getContent());
        model.addAttribute("totalpage", page.getTotalPages());
        model.addAttribute("currentpage", pageNo);
        model.addAttribute("keyword", keyword);

        return "admin/ungho/ungHo";
    }

    @GetMapping("/them")
    public String getThem(Model model) {
        model.addAttribute("form", new UngHoFormDTO());
        return "admin/ungho/them";
    }

    @PostMapping("/them")
    public String postThem(@ModelAttribute("form") UngHoFormDTO form, Model model) {
        try {
            ungHoService.create(form);
            return "redirect:/admin/UngHo";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "admin/ungho/them";
        }
    }

    @GetMapping("/sua/{id}")
    public String getSua(@PathVariable("id") Long id,
            @RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(name = "keyword", required = false) String keyword,
            Model model) {

        UngHoFormDTO form = ungHoService.getEditForm(id);
        if (form == null) {
            model.addAttribute("error", "Không tìm thấy ủng hộ ID: " + id);
            return "admin/ungho/sua";
        }

        model.addAttribute("form", form);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("keyword", keyword);
        return "admin/ungho/sua";
    }

    @PostMapping("/sua")
    public String postSua(@ModelAttribute("form") UngHoFormDTO form,
            @RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(name = "keyword", required = false) String keyword,
            Model model) {
        try {
            ungHoService.update(form);

            String url = "redirect:/admin/UngHo?pageNo=" + pageNo;
            if (keyword != null && !keyword.trim().isEmpty())
                url += "&keyword=" + keyword.trim();
            return url;

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("pageNo", pageNo);
            model.addAttribute("keyword", keyword);
            return "admin/ungho/sua";
        }
    }

    @PostMapping("/xoa/{id}")
    public String xoa(@PathVariable("id") Long id,
            @RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(name = "keyword", required = false) String keyword) {
        ungHoService.delete(id);

        String url = "redirect:/admin/UngHo?pageNo=" + pageNo;
        if (keyword != null && !keyword.trim().isEmpty())
            url += "&keyword=" + keyword.trim();
        return url;
    }

    @GetMapping("/{id}/giao-dich")
    public String giaoDich(@PathVariable("id") Long id,
            @RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(name = "trangThai", defaultValue = "ALL") String trangThai,
            Model model) {

        UngHo campaign = ungHoDonateService.getCampaign(id);
        if (campaign == null) {
            model.addAttribute("error", "Không tìm thấy chiến dịch ủng hộ ID: " + id);
            return "admin/ungho/giaoDich";
        }

        Page<GiaoDichUngHo> page = ungHoDonateService.pageGiaoDich(id, trangThai, pageNo, 6);

        model.addAttribute("campaign", campaign);
        model.addAttribute("dsGiaoDich", page.getContent());
        model.addAttribute("totalpage", page.getTotalPages());
        model.addAttribute("currentpage", pageNo);
        model.addAttribute("trangThai", trangThai);

        // thống kê nhanh
        model.addAttribute("countSuccess", ungHoDonateService.countSuccess(id));
        model.addAttribute("countPending", ungHoDonateService.countPending(id));
        model.addAttribute("sumSuccess", ungHoDonateService.sumSuccess(id));

        return "admin/ungho/giaoDich";
    }
}

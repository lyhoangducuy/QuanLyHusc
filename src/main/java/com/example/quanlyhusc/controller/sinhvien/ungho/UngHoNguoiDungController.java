package com.example.quanlyhusc.controller.sinhvien.ungho;


import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.quanlyhusc.entity.CustomUserDetails;
import com.example.quanlyhusc.entity.NguoiDung;
import com.example.quanlyhusc.entity.ungho.GiaoDichUngHo;
import com.example.quanlyhusc.entity.ungho.UngHo;
import com.example.quanlyhusc.repository.ungho.UngHoRepository;
import com.example.quanlyhusc.service.ungho.UngHoDonateService;

@Controller
public class UngHoNguoiDungController {

    @Autowired private UngHoRepository ungHoRepository;
    @Autowired private UngHoDonateService donateService;

    @GetMapping("/UngHo")
    public String list(@RequestParam(name="pageNo", defaultValue="1") int pageNo,
                       @RequestParam(name="keyword", required=false) String keyword,
                       Model model) {
        Page<UngHo> page = (keyword != null && !keyword.trim().isEmpty())
                ? ungHoRepository.findByTieuDeContainingIgnoreCase(keyword.trim(),
                    PageRequest.of(pageNo - 1, 6))
                : ungHoRepository.findAll(PageRequest.of(pageNo - 1, 6));

        model.addAttribute("dsUngHo", page.getContent());
        model.addAttribute("totalpage", page.getTotalPages());
        model.addAttribute("currentpage", pageNo);
        model.addAttribute("keyword", keyword);
        return "sinhvien/ungho/ungHo";
    }

    @GetMapping("/UngHo/{id}")
public String detail(@PathVariable("id") Long id,
                     @RequestParam(name="pageNo", defaultValue="1") int pageNo,
                     @RequestParam(name="trangThai", defaultValue="ALL") String trangThai,
                     Model model) {

    UngHo uh = ungHoRepository.findById(id).orElse(null);
    if (uh == null) {
        model.addAttribute("error", "Không tìm thấy chiến dịch");
        return "sinhvien/ungho/xem";
    }

    // ✅ page giao dịch theo chiến dịch (giống admin)
    Page<GiaoDichUngHo> page = donateService.pageGiaoDich(id, trangThai, pageNo, 6);

    model.addAttribute("u", uh);

    model.addAttribute("dsGiaoDich", page.getContent());
    model.addAttribute("totalpage", page.getTotalPages());
    model.addAttribute("currentpage", pageNo);
    model.addAttribute("trangThai", trangThai);

    return "sinhvien/ungho/xem";
}


    @PostMapping("/UngHo/{id}/donate")
    public String donate(@PathVariable("id") Long id,
                         @RequestParam("soTienVnd") Long soTienVnd,
                         @RequestParam(name="noiDung", required=false) String noiDung,
                         HttpServletRequest req,@AuthenticationPrincipal CustomUserDetails me) {

        

        String ip = req.getRemoteAddr();
        String url = donateService.taoGiaoDichVaLayUrl(id, soTienVnd, noiDung, me.getNguoiDungId(), ip);
        return "redirect:" + url;
    }
}


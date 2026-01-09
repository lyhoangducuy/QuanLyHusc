package com.example.quanlyhusc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.quanlyhusc.dto.nguoidung.DangKyDTO;
import com.example.quanlyhusc.dto.nguoidung.DatLaiMatKhauDTO;
import com.example.quanlyhusc.dto.nguoidung.QuenMatKhauDTO;
import com.example.quanlyhusc.service.nguoidung.NguoiDungService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NguoiDungController {

    @Autowired
    private NguoiDungService nguoiDungService;

    @GetMapping("/login")
    public String getLogin() {
        return "auth/login";
    }

    @GetMapping("/dangKy")
    public String getDangKy() {
        return "auth/dangKy";
    }

   @PostMapping("/dangKy")
public String postDangKy(@ModelAttribute("dangKyDTO") DangKyDTO dangKyDTO, Model model) {
    try {
        this.nguoiDungService.check(dangKyDTO);
        this.nguoiDungService.dangKy(dangKyDTO);
        this.nguoiDungService.taoOtpXacMinhVaGuiMail(dangKyDTO.getEmail());
        return "redirect:/xacNhan?email=" + dangKyDTO.getEmail();

    } catch (Exception e) {
        model.addAttribute("error", e.getMessage());
        model.addAttribute("dangKyDTO", dangKyDTO);
        return "auth/dangKy";
    }
}


    @GetMapping("/xacNhan")
    public String getXacNhan(@RequestParam("email") String email, Model model) {
        model.addAttribute("email", email);
        return "auth/xacNhan";
    }

    @PostMapping("/xacNhan")
    public String postXacNhan(@RequestParam("email") String email,
            @RequestParam("otp") String otp,
            RedirectAttributes ra) {
        try {
            nguoiDungService.xacMinhEmailBangOtp(email, otp);
            ra.addFlashAttribute("success", "Xác minh thành công! Bạn có thể đăng nhập.");
            return "redirect:/login";
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/xacNhan?email=" + email;
        }
    }
    @PostMapping("/xacNhan/gui-lai")
    public String guiLaiOtp(@RequestParam("email") String email,
            RedirectAttributes ra) {
        try {
            nguoiDungService.taoOtpXacMinhVaGuiMail(email);
            ra.addFlashAttribute("success", "Đã gửi lại OTP. Vui lòng kiểm tra email.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/xacNhan?email=" + email;
    }


 

    private String baseUrl(HttpServletRequest req) {
        // http://localhost:8080
        return req.getScheme() + "://" + req.getServerName()
                + ((req.getServerPort() == 80 || req.getServerPort() == 443) ? "" : ":" + req.getServerPort());
    }

    @GetMapping("/quenMatKhau")
    public String getQuen(Model model) {
        model.addAttribute("form", new QuenMatKhauDTO());
        return "auth/quenMatKhau";
    }

    @PostMapping("/quenMatKhau")
    public String postQuen(@ModelAttribute("form") QuenMatKhauDTO form,
                           HttpServletRequest req,
                           RedirectAttributes ra) {
        try {
            this.nguoiDungService.guiLinkQuenMatKhau(form.getEmail(), baseUrl(req));
            ra.addFlashAttribute("success", "Đã gửi link đặt lại mật khẩu. Vui lòng kiểm tra email.");
            return "redirect:/quenMatKhau";
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/quenMatKhau";
        }
    }

    @GetMapping("/datLaiMatKhauToken")
    public String getDatLai(@RequestParam("token") String token, Model model) {
        DatLaiMatKhauDTO form = new DatLaiMatKhauDTO();
        form.setToken(token);
        model.addAttribute("form", form);
        return "auth/datLaiMatKhau";
    }

    @PostMapping("/datLaiMatKhauToken")
    public String postDatLai(@ModelAttribute("form") DatLaiMatKhauDTO form,
                             RedirectAttributes ra) {
        try {
            this.nguoiDungService.datLaiMatKhau(form.getToken(), form.getMatKhauMoi(), form.getMatKhauNhapLai());
            ra.addFlashAttribute("success", "Đổi mật khẩu thành công! Bạn có thể đăng nhập.");
            return "redirect:/login";
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/datLaiMatKhauToken=" + form.getToken();
        }
    }
}
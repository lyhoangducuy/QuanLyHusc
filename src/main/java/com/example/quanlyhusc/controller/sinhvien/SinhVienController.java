package com.example.quanlyhusc.controller.sinhvien;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.quanlyhusc.dto.SuaThongTinCaNhanDTO;
import com.example.quanlyhusc.entity.CustomUserDetails;
import com.example.quanlyhusc.entity.NguoiDung;
import com.example.quanlyhusc.entity.baiviet.BaiViet;
import com.example.quanlyhusc.service.baiviet.BaiVietService;
import com.example.quanlyhusc.service.baiviet.BaiVietServiceImple;
import com.example.quanlyhusc.service.nguoidung.NguoiDungService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SinhVienController {
    @Autowired
    private BaiVietService baiVietService;
    @Autowired
    private NguoiDungService nguoiDungService;

    @GetMapping("/")
    public String getTrangChu(Model model, @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "pageNo", defaultValue = "1") int pageNo) {

        if (keyword != null && !keyword.isEmpty()) {
            Page<BaiViet> list = this.baiVietService.search(keyword, pageNo);
            model.addAttribute("dsBaiViet", list.getContent());
            model.addAttribute("totalpage", list.getTotalPages());
            model.addAttribute("currentpage", pageNo);
            model.addAttribute("baiVietGhim", null);
            model.addAttribute("keyword", keyword);
        } else {
            Page<BaiViet> list = this.baiVietService.getAll(pageNo);
            model.addAttribute("dsBaiViet", list.getContent());
            model.addAttribute("totalpage", list.getTotalPages());
            model.addAttribute("currentpage", pageNo);
            model.addAttribute("dsBaiVietGhim", this.baiVietService.findByGhimIsTrue());
        }
        return "sinhvien/trangchu/trangChu";
    }

    @GetMapping("/BaiViet/{id}")
    public String getBaiViet(@PathVariable("id") Long id, Model model) {
        BaiViet baiViet = baiVietService.findByIdFetchDsTep(id);
        model.addAttribute("baiViet", baiViet);
        return "sinhvien/trangchu/chiTietBaiViet";
    }

    @GetMapping("/chinhSuaThongTin")
    public String editMe(Model model, @AuthenticationPrincipal CustomUserDetails principal) {
        NguoiDung me = this.nguoiDungService.findById(principal.getNguoiDungId());

        SuaThongTinCaNhanDTO dto = new SuaThongTinCaNhanDTO();
        dto.setHoTen(me.getHoTen());
        dto.setEmail(me.getEmail());
        dto.setSoDienThoai(me.getSoDienThoai());
        dto.setTenDangNhap(me.getTenDangNhap());
        model.addAttribute("form", dto); // ✅ bắt buộc đúng tên "form"
        return "sinhvien/chinhSua";
    }

    @PostMapping("/chinhSuaThongTin")
    public String updateMe(@Valid @ModelAttribute("form") SuaThongTinCaNhanDTO form,
            BindingResult br,
            @AuthenticationPrincipal CustomUserDetails principal,
            RedirectAttributes ra) {

        if (br.hasErrors())
            return "sinhvien/chinhSua";

        try {
            nguoiDungService.updateProfile(
                    principal.getNguoiDungId(),
                    form.getTenDangNhap(),
                    form.getHoTen(),
                    form.getEmail(),
                    form.getSoDienThoai());

            ra.addFlashAttribute("success", "Cập nhật thông tin thành công!");

            // ✅ HƯỚNG 1 (đơn giản): bắt user đăng nhập lại để refresh username trên navbar
            ra.addFlashAttribute("success", "Đã cập nhật. Vui lòng đăng nhập lại để cập nhật phiên hiển thị.");
            return "redirect:/chinhSuaThongTin";

           
        } catch (IllegalArgumentException ex) {
            if ("USERNAME_EXISTS".equals(ex.getMessage())) {
                br.rejectValue("tenDangNhap", "USERNAME_EXISTS", "Tên đăng nhập đã tồn tại, chọn tên khác nha");
                return "sinhvien/chinhSua";
            }
            if ("EMAIL_EXISTS".equals(ex.getMessage())) {
                br.rejectValue("email", "EMAIL_EXISTS", "Email đã tồn tại, vui lòng chọn email khác");
                return "sinhvien/chinhSua";
            }
            br.reject("UNKNOWN", "Có lỗi xảy ra, vui lòng thử lại.");
            return "sinhvien/chinhSua";
        }
    }

}
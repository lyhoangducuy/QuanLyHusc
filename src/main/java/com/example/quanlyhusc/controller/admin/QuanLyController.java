package com.example.quanlyhusc.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.quanlyhusc.dto.SuaThongTinCaNhanDTO;
import com.example.quanlyhusc.entity.CustomUserDetails;
import com.example.quanlyhusc.entity.NguoiDung;
import com.example.quanlyhusc.entity.phananh.TrangThaiPhanAnh;
import com.example.quanlyhusc.service.baiviet.BaiVietService;
import com.example.quanlyhusc.service.nguoidung.NguoiDungService;
import com.example.quanlyhusc.service.phananh.PhanAnhService;
import com.example.quanlyhusc.service.ungho.UngHoService;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/admin")
public class QuanLyController {
    @Autowired
    private NguoiDungService nguoiDungService;
    @Autowired
    private PhanAnhService phanAnhService;
    @Autowired 
    private UngHoService ungHoService;
    @Autowired
    private BaiVietService baiVietService;
    @GetMapping("/")
    public String getHome() {
        return "redirect:/admin";
    }
    @GetMapping("")
    public String getMethodName(Model model) {
        model.addAttribute("tongPhanAnh",this.phanAnhService.dem());
        model.addAttribute("tongUngHo",this.ungHoService.dem());
        model.addAttribute("tongBaiViet",this.baiVietService.dem());
        model.addAttribute("tongNguoiDung",this.nguoiDungService.dem());

        model.addAttribute("chartTitle", "Bieu do phan anh");

        model.addAttribute("chartLabels", List.of("Cho xac nhan", "Da xac nhan", "Dang xu ly", "Da hoan thanh","Tu choi"));
        model.addAttribute("chartData", List.of(this.phanAnhService.demTheoTinhTrang(TrangThaiPhanAnh.CHO_XAC_NHAN), 
                                                this.phanAnhService.demTheoTinhTrang(TrangThaiPhanAnh.DA_XAC_NHAN), 
                                                this.phanAnhService.demTheoTinhTrang(TrangThaiPhanAnh.DANG_XU_LY), 
                                                this.phanAnhService.demTheoTinhTrang(TrangThaiPhanAnh.DA_HOAN_THANH),
                                                this.phanAnhService.demTheoTinhTrang(TrangThaiPhanAnh.TU_CHOI)));

        Long t1=this.nguoiDungService.demNguoiDungTuanTruoc(0);
        Long t2=this.nguoiDungService.demNguoiDungTuanTruoc(1);
        Long t3=this.nguoiDungService.demNguoiDungTuanTruoc(2);
        Long t4=this.nguoiDungService.demNguoiDungTuanTruoc(3);
        model.addAttribute("lineTitle2", "Nguoi dung moi theo tuan");
        model.addAttribute("lineLabels2", List.of("3 Tuan truoc","2 Tuan truoc","1 tuan truoc","Tuan nay"));
        model.addAttribute("lineValues2", List.of(t4, t3, t2, t1));

        Long b1=this.baiVietService.demNguoiDungTuanTruoc(0);
        Long b2=this.baiVietService.demNguoiDungTuanTruoc(1);
        Long b3=this.baiVietService.demNguoiDungTuanTruoc(2);
        Long b4=this.baiVietService.demNguoiDungTuanTruoc(3);
        model.addAttribute("lineTitle3", "Bai viet moi theo tuan");
        model.addAttribute("lineLabels3", List.of("3 Tuan truoc","2 Tuan truoc","1 tuan truoc","Tuan nay"));
        model.addAttribute("lineValues3", List.of(b4, b3, b2, b1));
        return "admin/DashBoardAdmin";
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
        return "admin/chinhSua";
    }

    @PostMapping("/chinhSuaThongTin")
    public String updateMe(@Valid @ModelAttribute("form") SuaThongTinCaNhanDTO form,
            BindingResult br,
            @AuthenticationPrincipal CustomUserDetails principal,
            RedirectAttributes ra) {

        if (br.hasErrors())
            return "admin/chinhSua";

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
            return "redirect:/admin/chinhSuaThongTin";

           
        } catch (IllegalArgumentException ex) {
            if ("USERNAME_EXISTS".equals(ex.getMessage())) {
                br.rejectValue("tenDangNhap", "USERNAME_EXISTS", "Tên đăng nhập đã tồn tại, chọn tên khác nha");
                return "admin/chinhSua";
            }
            if ("EMAIL_EXISTS".equals(ex.getMessage())) {
                br.rejectValue("email", "EMAIL_EXISTS", "Email đã tồn tại, vui lòng chọn email khác");
                return "admin/chinhSua";
            }
            br.reject("UNKNOWN", "Có lỗi xảy ra, vui lòng thử lại.");
            return "admin/chinhSua";
        }
    }
}

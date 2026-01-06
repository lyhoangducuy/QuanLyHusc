package com.example.quanlyhusc.controller.sinhvien.phananh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.quanlyhusc.dto.baiviet.BaiVietDTO;
import com.example.quanlyhusc.dto.phananh.PhanAnhDTO;
import com.example.quanlyhusc.entity.NguoiDung;
import com.example.quanlyhusc.entity.phananh.PhanAnh;
import com.example.quanlyhusc.service.nguoidung.NguoiDungService;
import com.example.quanlyhusc.service.phananh.LoaiPhanAnhService;
import com.example.quanlyhusc.service.phananh.LoaiPhanAnhServiceImple;
import com.example.quanlyhusc.service.phananh.PhanAnhServiceImple;
import com.example.quanlyhusc.service.phananh.PhanAnhService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/PhanAnh")
public class SVPhanAnhController {
    @Autowired
    private PhanAnhService phanAnhService;
    @Autowired
    private LoaiPhanAnhService loaiPhanAnhService;
    @Autowired
    private NguoiDungService nguoiDungService;

    @GetMapping("")
    public String getPhanAnh(Model model,@RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "pageNo", defaultValue = "1") int pageNo ) {
        Page<PhanAnh> list=null;
        if (keyword != null && !keyword.isEmpty()) {
            list=this.phanAnhService.searchByTieuDe(keyword,pageNo);
            model.addAttribute("keyword", keyword);
        } else {
            list=phanAnhService.getAllPhanAnh(pageNo);
        }
        model.addAttribute("dsPhanAnh", list.getContent());
        model.addAttribute("currentpage", pageNo);
        model.addAttribute("totalpage", list.getTotalPages());
        return "sinhvien/phananh/phanAnh";
    }
    @GetMapping("/xem/{id}")
    public String getChiTiet(Model model, @PathVariable("id") Long id) {
        model.addAttribute("phanAnh",phanAnhService.findById(id));
        return "sinhvien/phananh/xemPhanAnh";
    }
    @GetMapping("/them")
    public String them(Model model) {
        model.addAttribute("dsLoaiPhanAnh", loaiPhanAnhService.getAll());
        return "sinhvien/phananh/themPhanAnh";
    }
    @PostMapping("/them")
    public String postThem(@ModelAttribute PhanAnhDTO phanAnhDTO,@RequestParam("files") MultipartFile[] file) {
        phanAnhService.create(phanAnhDTO,file);
        return "redirect:/PhanAnh";
    }
    @GetMapping("/lich-su/{id}")
    public String getLichSu(Model model,@PathVariable("id") Long id,@RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "pageNo", defaultValue = "1") int pageNo) {
        Page<PhanAnh> list=null;
        NguoiDung nguoiDung=this.nguoiDungService.findById(id);
        if (keyword != null && !keyword.isEmpty()) {
            list=this.phanAnhService.findByNguoiGuiAndTieuDeContainingIgnoreCase(nguoiDung, keyword, pageNo);
            model.addAttribute("keyword", keyword);
        } else {
            list=phanAnhService.getByNguoiGui(nguoiDung, pageNo);
        }
        model.addAttribute("dsPhanAnh", list.getContent());
        model.addAttribute("currentpage", pageNo);
        model.addAttribute("totalpage", list.getTotalPages());
        return "sinhvien/phananh/lichSu";
    }
    
    
    
}

package com.example.quanlyhusc.controller.admin.phananh;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.quanlyhusc.dto.baiviet.BaiVietDTO;
import com.example.quanlyhusc.dto.phananh.PhanAnhDTO;
import com.example.quanlyhusc.entity.phananh.PhanAnh;
import com.example.quanlyhusc.service.phananh.PhanAnhService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




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
    @PostMapping("/xoa/{id}")
    public String xoa(@PathVariable("id") Long id) {
        this.phanAnhService.deleteById(id);
        return "redirect:/admin/PhanAnh";
    }
    @PostMapping("/capNhatTrangThai/{id}")
    public String capNhatTrangThai(@PathVariable("id") Long id, @RequestParam("trangThai") String trangThai) {
        this.phanAnhService.updateTrangThai(id, trangThai);
        return "redirect:/admin/PhanAnh";
    }
    @GetMapping("/sua/{id}")
    public String sua(@PathVariable("id")Long id,Model model) {
       
        model.addAttribute("phanAnh",this.phanAnhService.findById(id));
        return "admin/phananh/suaPhanAnh";
    }
    @PostMapping("/sua/{id}")
    public String postMethodName(@PathVariable("id") Long id,@ModelAttribute PhanAnhDTO phanAnhDTO,@RequestParam("files") MultipartFile[] files,
        @RequestParam("xoaTepIds")List<Long> dsTepXoa) {
        this.phanAnhService.update(id,phanAnhDTO, files, dsTepXoa);
        
        return "redirect:/admin/PhanAnh/sua/"+id;
    }
    
    

    
    
}

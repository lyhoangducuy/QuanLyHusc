package com.example.quanlyhusc.controller.sinhvien.phananh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.quanlyhusc.dto.phananh.BinhLuanPhanAnhDTO;
import com.example.quanlyhusc.entity.phananh.BinhLuanPhanAnh;
import com.example.quanlyhusc.service.baiviet.BinhLuanPhanAnhService;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class BinhLuanPhanAnhController {
    @Autowired
    private BinhLuanPhanAnhService binhLuanPhanAnhService;
    @PostMapping("/PhanAnh/binhluan")
    public String taoBinhLuan(@ModelAttribute BinhLuanPhanAnhDTO binhLuanPhanAnhDTO,MultipartFile[] files){
        binhLuanPhanAnhService.taoBinhLuanPhanAnh(binhLuanPhanAnhDTO,files);
        return "redirect:/PhanAnh/xem/" +binhLuanPhanAnhDTO.getPhanAnhId();
    }
    
}

package com.example.quanlyhusc.service.baiviet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.quanlyhusc.dto.phananh.BinhLuanPhanAnhDTO;
import com.example.quanlyhusc.entity.baiviet.TepDinhKemBaiViet;
import com.example.quanlyhusc.entity.phananh.BinhLuanPhanAnh;
import com.example.quanlyhusc.entity.phananh.TepDinhKemBinhLuan;
import com.example.quanlyhusc.repository.baiviet.BinhLuanPhanAnhRepository;
import com.example.quanlyhusc.service.nguoidung.NguoiDungService;
import com.example.quanlyhusc.service.phananh.PhanAnhService;
import com.example.quanlyhusc.service.uploadfile.StorageService;

@Service
public class BinhLuanPhanAnhImple implements BinhLuanPhanAnhService {

    @Autowired
    private PhanAnhService phanAnhService;
    @Autowired
    private NguoiDungService nguoiDungService;
    @Autowired
    private BinhLuanPhanAnhRepository binhLuanPhanAnhRepository;
    @Autowired
    private StorageService storageService;

    @Override
    public Boolean taoBinhLuanPhanAnh(BinhLuanPhanAnhDTO binhLuanPhanAnhDTO,MultipartFile[] files) {
        try {
            BinhLuanPhanAnh binhLuanPhanAnh=new BinhLuanPhanAnh();
            binhLuanPhanAnh.setNoiDung(binhLuanPhanAnhDTO.getNoiDung());
            binhLuanPhanAnh.setPhanAnh(phanAnhService.findById(binhLuanPhanAnhDTO.getPhanAnhId()));
            binhLuanPhanAnh.setTacGia(nguoiDungService.findById(binhLuanPhanAnhDTO.getTacGiaId()));
            binhLuanPhanAnh.setTepDinhKemBinhLuans(binhLuanPhanAnhDTO.getTepDinhKemBinhLuans());
            if (files != null) {
                for (MultipartFile file : files) {
                    if (file == null || file.isEmpty()) continue;

                    // store xong lấy URL thật (khuyên m store trả về url)
                    storageService.store(file,"fileBaiViet");

                    TepDinhKemBinhLuan tep = new TepDinhKemBinhLuan();
                    tep.setTenTep(file.getOriginalFilename());
                    tep.setDuongDanUrl("/uploads/fileBaiViet/" + file.getOriginalFilename());
                    tep.setKichThuoc(file.getSize());

                    String ct = file.getContentType();
                    String loai = (ct != null && ct.startsWith("image/")) ? "IMAGE"
                            : (ct != null && ct.startsWith("video/")) ? "VIDEO"
                            : "FILE";
                    tep.setLoaiTep(loai);

                    // ✅ QUAN TRỌNG: set FK
                    tep.setBinhLuanPhanAnh(binhLuanPhanAnh);

                    // ✅ add vào danh sách con
                    binhLuanPhanAnh.getTepDinhKemBinhLuans().add(tep);
                }
            }
            binhLuanPhanAnhRepository.save(binhLuanPhanAnh);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean xoaBinhLuanPhanAnh(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'xoaBinhLuanPhanAnh'");
    }

    @Override
    public Boolean suaBinhLuanPhanAnh(BinhLuanPhanAnh binhLuanPhanAnh) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'suaBinhLuanPhanAnh'");
    }
    
}

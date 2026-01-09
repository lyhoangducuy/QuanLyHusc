package com.example.quanlyhusc.service.phananh;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.example.quanlyhusc.dto.phananh.PhanAnhDTO;
import com.example.quanlyhusc.entity.NguoiDung;
import com.example.quanlyhusc.entity.phananh.PhanAnh;
import com.example.quanlyhusc.entity.phananh.TrangThaiPhanAnh;

public interface PhanAnhService {
    Page<PhanAnh> getAllPhanAnh(int pageNo);
    PhanAnh findById(Long id);
    Boolean create(PhanAnhDTO phanAnhDTO,MultipartFile[] file);
    Boolean updateTrangThai(Long id, String trangThai);
    Page<PhanAnh> searchByTieuDe(String keyword, int pageNo);
    Boolean deleteById(Long id);
    List<PhanAnh> getAll();
    Boolean update(Long id,PhanAnhDTO phanAnhDTO,MultipartFile[] file,List<Long> dsTepXoa );
    Page<PhanAnh> getByNguoiGui(NguoiDung nguoiDung,int pageNo);
    Page<PhanAnh> findByNguoiGuiAndTieuDeContainingIgnoreCase(NguoiDung nguoiDung,String tieuDe,int pageNo);
    Long dem();
    Long demTheoTinhTrang(TrangThaiPhanAnh trangThaiPhanAnh);
    
}

package com.example.quanlyhusc.service.baiviet;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.quanlyhusc.dto.baiviet.BaiVietDTO;
import com.example.quanlyhusc.entity.baiviet.BaiViet;
import com.example.quanlyhusc.entity.baiviet.TepDinhKemBaiViet;
import com.example.quanlyhusc.repository.NguoiDungRepository;
import com.example.quanlyhusc.repository.baiviet.BaiVietRepository;
import com.example.quanlyhusc.service.uploadfile.FileSystemStorageService;
import com.example.quanlyhusc.service.uploadfile.StorageService;
@Service
public class BaiVietService implements BaiVietServiceImple {

    @Autowired
    private BaiVietRepository baiVietRepository;
    @Autowired
    private NguoiDungRepository nguoiDungRepository;
    @Autowired
    private StorageService storageService;

    @Override
    public List<BaiViet> getAll() {
        return this.baiVietRepository.findAllForList();
    }

    @Override
    public BaiViet findById(Long id) {
        return this.baiVietRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean update(Long id, BaiVietDTO baiVietDTO) {
        BaiViet baiViet = this.baiVietRepository.findById(id).orElse(null);
        if(baiViet==null) return false;
        else {
            baiViet.setTieuDe(baiVietDTO.getTieuDe());
            baiViet.setNoiDung(baiVietDTO.getNoiDung());
            baiViet.setGhim(baiVietDTO.isGhim());
            baiViet.setDsTep(baiVietDTO.getDsTep());
            this.baiVietRepository.save(baiViet);
            return true;
        }
    }
    @Override
    public Boolean deleteById(Long id) {
        try {
            this.baiVietRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }
    }
   

    @Transactional
    @Override
    public Boolean create(BaiVietDTO dto, MultipartFile[] files) {
        try {
            BaiViet baiViet = new BaiViet();
            baiViet.setTieuDe(dto.getTieuDe());
            baiViet.setNoiDung(dto.getNoiDung());
            baiViet.setGhim(dto.isGhim());
            baiViet.setPhamViHienThi("ALL");
            baiViet.setTacGiaId(nguoiDungRepository.findByNguoiDungId(dto.getTacGiaId()));

            if (files != null) {
                for (MultipartFile file : files) {
                    if (file == null || file.isEmpty()) continue;

                    // store xong lấy URL thật (khuyên m store trả về url)
                    storageService.store(file);

                    TepDinhKemBaiViet tep = new TepDinhKemBaiViet();
                    tep.setTenTep(file.getOriginalFilename());
                    tep.setDuongDanUrl("/uploads/fileBaiViet/" + file.getOriginalFilename());
                    tep.setKichThuoc(file.getSize());

                    String ct = file.getContentType();
                    String loai = (ct != null && ct.startsWith("image/")) ? "IMAGE"
                            : (ct != null && ct.startsWith("video/")) ? "VIDEO"
                            : "FILE";
                    tep.setLoaiTep(loai);

                    // ✅ QUAN TRỌNG: set FK
                    tep.setBaiViet(baiViet);

                    // ✅ add vào danh sách con
                    baiViet.getDsTep().add(tep);
                }
            }

            baiVietRepository.save(baiViet); // ✅ tự insert BaiViet + insert Tep
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public BaiViet findByIdFetchDsTep(Long id) {
        try {
            return this.baiVietRepository.findByIdFetchDsTep(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public BaiViet findByGhimIsTrue() {
        return this.baiVietRepository.findByGhimIsTrue();
    }


   
}

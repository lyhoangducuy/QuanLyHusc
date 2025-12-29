package com.example.quanlyhusc.service.baiviet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quanlyhusc.dto.baiviet.BaiVietDTO;
import com.example.quanlyhusc.entity.baiviet.BaiViet;
import com.example.quanlyhusc.repository.NguoiDungRepository;
import com.example.quanlyhusc.repository.baiviet.BaiVietRepository;
@Service
public class BaiVietService implements BaiVietServiceImple {

    @Autowired
    private BaiVietRepository baiVietRepository;
    @Autowired
    private NguoiDungRepository nguoiDungRepository;

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
   

    @Override
    public Boolean create(BaiVietDTO baiVietDTO) {
        try {
            BaiViet baiViet = new BaiViet();
            baiViet.setTieuDe(baiVietDTO.getTieuDe());
            baiViet.setNoiDung(baiVietDTO.getNoiDung());
            baiViet.setGhim(baiVietDTO.isGhim());
            baiViet.setPhamViHienThi("ALL");
            baiViet.setDsTep(baiVietDTO.getDsTep());
            baiViet.setTacGiaId(nguoiDungRepository.findByNguoiDungId(baiVietDTO.getTacGiaId()));
            this.baiVietRepository.save(baiViet);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

   
}

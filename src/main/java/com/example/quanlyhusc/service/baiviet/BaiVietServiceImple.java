package com.example.quanlyhusc.service.baiviet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.quanlyhusc.dto.baiviet.BaiVietDTO;
import com.example.quanlyhusc.entity.baiviet.BaiViet;
import com.example.quanlyhusc.entity.baiviet.BaiVietDanhMuc;
import com.example.quanlyhusc.entity.baiviet.DanhMucBaiViet;
import com.example.quanlyhusc.entity.baiviet.PhamViHienThi;
import com.example.quanlyhusc.entity.baiviet.TepDinhKemBaiViet;
import com.example.quanlyhusc.entity.phananh.PhanAnh;
import com.example.quanlyhusc.entity.phananh.TepDinhKemPhanAnh;
import com.example.quanlyhusc.repository.NguoiDungRepository;
import com.example.quanlyhusc.repository.baiviet.BaiVietRepository;
import com.example.quanlyhusc.repository.baiviet.TepDinhKemBaiVietRepository;
import com.example.quanlyhusc.service.uploadfile.StorageService;
@Service
public class BaiVietServiceImple implements BaiVietService {

    @Autowired
    private BaiVietRepository baiVietRepository;
    @Autowired
    private NguoiDungRepository nguoiDungRepository;
    @Autowired
    private StorageService storageService;
    @Autowired
    private TepDinhKemBaiVietRepository tepDinhKemBaiVietRepository;
    @Autowired
    private DanhMucBaiVietService danhMucBaiVietService;

    @Override
    public List<BaiViet> getAll() {
        return this.baiVietRepository.findAllForList();
    }

    @Override
    public BaiViet findById(Long id) {
        return this.baiVietRepository.findByBaiVietId(id);
    }

    @Override
    public Boolean update(Long id, BaiVietDTO baiVietDTO, MultipartFile[] files,List<Long> dsTepXoa) {
        BaiViet baiViet = this.baiVietRepository.findById(id).orElse(null);
        if(baiViet==null) return false;
        else {
            baiViet.setTieuDe(baiVietDTO.getTieuDe());
            baiViet.setNoiDung(baiVietDTO.getNoiDung());
            Set<BaiVietDanhMuc> danhMucBaiViets=new HashSet<>();

            List<Long> dmIds = baiVietDTO.getDanhMucIds();
            if (dmIds != null) {
                for (Long dmid : dmIds) {
                    DanhMucBaiViet dm = danhMucBaiVietService.findById(dmid); // phải trả ENTITY
                    danhMucBaiViets.add(new BaiVietDanhMuc(baiViet, dm)); // ✅ constructor set EmbeddedId
                }
            }
            baiViet.setDsDanhMuc(danhMucBaiViets);
            baiViet.setGhim(baiVietDTO.isGhim());
            // 5) Xoá tệp đính kèm theo danh sách id từ client
            if (dsTepXoa != null && !dsTepXoa.isEmpty()) {

                // chỉ xoá những tệp thuộc đúng phản ánh (tránh xoá nhầm của phản ánh khác)
                List<TepDinhKemBaiViet> tepCanXoa =
                        tepDinhKemBaiVietRepository.findByIdInAndBaiViet_BaiVietId(dsTepXoa, id);

                for (TepDinhKemBaiViet t : tepCanXoa) {
                    // xoá file vật lý (nếu storageService có)
                    try {
                        storageService.delete(t.getDuongDanUrl()); // nếu m chưa có delete thì comment dòng này
                    } catch (Exception ignore) {}

                    // xoá DB
                    tepDinhKemBaiVietRepository.delete(t);
                }
            }

            // 6) Thêm file mới
            // 6) Thêm file mới
            if (files != null) {
                for (MultipartFile f : files) {
                    if (f == null || f.isEmpty()) continue;

                    // ✅ nhận URL thật (tên đã đổi)
                    String url = storageService.store(f, "fileBaiViet");

                    TepDinhKemBaiViet tep = new TepDinhKemBaiViet();
                    tep.setBaiViet(baiViet);

                    tep.setTenTep(f.getOriginalFilename()); // tên hiển thị
                    tep.setDuongDanUrl(url);                // ✅ url chuẩn theo tên mới
                    tep.setKichThuoc(f.getSize());

                    String ct = (f.getContentType() == null) ? "" : f.getContentType();
                    if (ct.startsWith("image/")) tep.setLoaiTep("IMAGE");
                    else if (ct.startsWith("video/")) tep.setLoaiTep("VIDEO");
                    else tep.setLoaiTep("FILE");

                    tepDinhKemBaiVietRepository.save(tep);
                }
            }

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
            baiViet.setPhamViHienThi(PhamViHienThi.ALL);
            baiViet.setTacGiaId(nguoiDungRepository.findByNguoiDungId(dto.getTacGiaId()));

            if (files != null) {
                for (MultipartFile file : files) {
                    if (file == null || file.isEmpty()) continue;

                    // store xong lấy URL thật (khuyên m store trả về url)
                    storageService.store(file,"fileBaiViet");

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

    @Override
    public List<BaiViet> searchByTieuDe(String keyword) {
        return this.baiVietRepository.searchByTieuDe(keyword);
    }
    @Override
    public Page<BaiViet> getAll(int pageNo) {
        PageRequest pageable = PageRequest.of(pageNo - 1, 6);
        return baiVietRepository.getAllPage(pageable);
    }
    @Override
    public Page<BaiViet> search(String keyword, int pageNo) {
        PageRequest pageable = PageRequest.of(pageNo - 1, 6);
        return baiVietRepository.findByTieuDeContainingIgnoreCase(keyword, pageable);
    }

    @Override
    public BaiViet findByBaiVietId(Long id) {
        return this.baiVietRepository.findByBaiVietId(id);
    }


   
}

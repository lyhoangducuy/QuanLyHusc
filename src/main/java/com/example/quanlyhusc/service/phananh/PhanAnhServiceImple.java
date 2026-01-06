package com.example.quanlyhusc.service.phananh;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.quanlyhusc.dto.phananh.PhanAnhDTO;
import com.example.quanlyhusc.entity.NguoiDung;
import com.example.quanlyhusc.entity.phananh.LoaiPhanAnh;
import com.example.quanlyhusc.entity.phananh.PhanAnh;
import com.example.quanlyhusc.entity.phananh.TepDinhKemPhanAnh;
import com.example.quanlyhusc.entity.phananh.TrangThaiPhanAnh;
import com.example.quanlyhusc.repository.phananh.PhanAnhRepository;
import com.example.quanlyhusc.repository.phananh.TepDinhKemPhanAnhRepository;
import com.example.quanlyhusc.service.nguoidung.NguoiDungService;
import com.example.quanlyhusc.service.uploadfile.StorageService;

@Service
public class PhanAnhServiceImple implements PhanAnhService {
    @Autowired
    private PhanAnhRepository phanAnhRepository;
    @Autowired
    private StorageService storageService;
    @Autowired
    private LoaiPhanAnhService loaiPhanAnhService;
    @Autowired
    private NguoiDungService nguoiDungService;
    @Autowired
    private TepDinhKemPhanAnhRepository tepDinhKemPhanAnhRepository;
    @Override
    public Page<PhanAnh> getAllPhanAnh(int pageNo) {
        Pageable pageable = Pageable.ofSize(6).withPage(pageNo - 1);
        return phanAnhRepository.findAll(pageable);
    }

    @Override
    public PhanAnh findById(Long id) {
        return this.phanAnhRepository.findByPhanAnhId(id);
    }
    @Transactional
    @Override
    public Boolean create(PhanAnhDTO phanAnhDTO,MultipartFile[] files) {
       
        try {
            PhanAnh phanAnh = new PhanAnh();
            phanAnh.setTieuDe(phanAnhDTO.getTieuDe());
            phanAnh.setMoTa(phanAnhDTO.getMoTa());
            phanAnh.setDiaChiMoTa(phanAnhDTO.getDiaChiMoTa());
            phanAnh.setLoai(loaiPhanAnhService.findById(phanAnhDTO.getLoaiPhanAnhId()));
            phanAnh.setNguoiGui(nguoiDungService.findById(phanAnhDTO.getNguoiGui()));
            
            if (files != null) {
                for (MultipartFile file : files) {
                    if (file == null || file.isEmpty()) continue;

                    // ✅ Lưu file và nhận URL thật (tên đã đổi)
                    String url = storageService.store(file, "filePhanAnh");

                    TepDinhKemPhanAnh tep = new TepDinhKemPhanAnh();
                    tep.setTenTep(file.getOriginalFilename()); // tên hiển thị
                    tep.setDuongDanUrl(url);                   // ✅ url đúng file đã lưu
                    tep.setKichThuoc(file.getSize());

                    String ct = file.getContentType();
                    String loai = (ct != null && ct.startsWith("image/")) ? "IMAGE"
                            : (ct != null && ct.startsWith("video/")) ? "VIDEO"
                            : "FILE";
                    tep.setLoaiTep(loai);

                    tep.setPhanAnh(phanAnh);
                    phanAnh.getTepDinhKem().add(tep);
                }
            }
            phanAnhRepository.save(phanAnh);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateTrangThai(Long id, String trangThai) {
        try {
            PhanAnh phanAnh = this.phanAnhRepository.findById(id).orElse(null);
            if (phanAnh == null) {
                return false;
            }
            phanAnh.setTrangThai(TrangThaiPhanAnh.valueOf(trangThai));
            phanAnh.preUpdate();
            this.phanAnhRepository.save(phanAnh);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Page<PhanAnh> searchByTieuDe(String keyword, int pageNo) {
        PageRequest pageable = PageRequest.of(pageNo - 1, 10);
        return phanAnhRepository.findByTieuDeContainingIgnoreCase(keyword, pageable);
    }

    @Override
    public Boolean deleteById(Long id) {
        try {
            this.phanAnhRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<PhanAnh> getAll() {
        return this.phanAnhRepository.findAll();
    }

    @Override
    @Transactional
    public Boolean update(Long id, PhanAnhDTO phanAnhDTO, MultipartFile[] files, List<Long> dsTepXoa) {
        try {
            // 1) Lấy phản ánh
            PhanAnh phanAnh = phanAnhRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy phản ánh id=" + id));

            // 2) Update các field text
            phanAnh.setTieuDe(phanAnhDTO.getTieuDe());
            phanAnh.setMoTa(phanAnhDTO.getMoTa());
            phanAnh.setDiaChiMoTa(phanAnhDTO.getDiaChiMoTa());

            // 3) Update loại phản ánh (nếu có)
            if (phanAnhDTO.getLoaiPhanAnhId() != null) {
                LoaiPhanAnh loai = loaiPhanAnhService.findById(phanAnhDTO.getLoaiPhanAnhId());
                phanAnh.setLoai(loai);
            }

            // 5) Xoá tệp đính kèm theo danh sách id từ client
            if (dsTepXoa != null && !dsTepXoa.isEmpty()) {

                // chỉ xoá những tệp thuộc đúng phản ánh (tránh xoá nhầm của phản ánh khác)
                List<TepDinhKemPhanAnh> tepCanXoa =
                        tepDinhKemPhanAnhRepository.findByTepIdInAndPhanAnh_PhanAnhId(dsTepXoa, id);

                for (TepDinhKemPhanAnh t : tepCanXoa) {
                    // xoá file vật lý (nếu storageService có)
                    try {
                        storageService.delete(t.getDuongDanUrl()); // nếu m chưa có delete thì comment dòng này
                    } catch (Exception ignore) {}

                    // xoá DB
                    tepDinhKemPhanAnhRepository.delete(t);
                }
            }

            // 6) Thêm file mới
            // 6) Thêm file mới
            if (files != null) {
                for (MultipartFile f : files) {
                    if (f == null || f.isEmpty()) continue;

                    // ✅ nhận URL thật (tên đã đổi)
                    String url = storageService.store(f, "filePhanAnh");

                    TepDinhKemPhanAnh tep = new TepDinhKemPhanAnh();
                    tep.setPhanAnh(phanAnh);

                    tep.setTenTep(f.getOriginalFilename()); // tên hiển thị
                    tep.setDuongDanUrl(url);                // ✅ url chuẩn theo tên mới
                    tep.setKichThuoc(f.getSize());

                    String ct = (f.getContentType() == null) ? "" : f.getContentType();
                    if (ct.startsWith("image/")) tep.setLoaiTep("IMAGE");
                    else if (ct.startsWith("video/")) tep.setLoaiTep("VIDEO");
                    else tep.setLoaiTep("FILE");

                    tepDinhKemPhanAnhRepository.save(tep);
                }
            }


            // 7) Save phản ánh
            phanAnhRepository.save(phanAnh);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Page<PhanAnh> getByNguoiGui(NguoiDung nguoiDung,int pageNo) {
        Pageable pageable = Pageable.ofSize(6).withPage(pageNo - 1);
        return this.phanAnhRepository.findByNguoiGui(nguoiDung,pageable);
    }

    @Override
    public Page<PhanAnh> findByNguoiGuiAndTieuDeContainingIgnoreCase(NguoiDung nguoiDung, String tieuDe, int pageNo) {
        Pageable pageable = Pageable.ofSize(6).withPage(pageNo - 1);
        return this.phanAnhRepository.findByNguoiGuiAndTieuDeContainingIgnoreCase(nguoiDung, tieuDe, pageable);
    }


    
}

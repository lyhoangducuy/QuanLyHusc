package com.example.quanlyhusc.service.ungho;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.quanlyhusc.dto.ungho.UngHoFormDTO;
import com.example.quanlyhusc.entity.ungho.UngHo;
import com.example.quanlyhusc.repository.ungho.UngHoRepository;

@Service
public class UngHoServiceImpl implements UngHoService {

    @Autowired
    private UngHoRepository ungHoRepository;

    @Override
    public Page<UngHo> list(String keyword, int pageNo, int pageSize) {
        PageRequest pageable = PageRequest.of(Math.max(pageNo - 1, 0), pageSize);

        if (keyword != null && !keyword.trim().isEmpty()) {
            return ungHoRepository.findByTieuDeContainingIgnoreCase(keyword.trim(), pageable);
        }
        return ungHoRepository.findAll(pageable);
    }

    @Override
    public UngHo findById(Long id) {
        return ungHoRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void create(UngHoFormDTO dto) {
        validate(dto);

        UngHo u = new UngHo();
        u.setTieuDe(dto.getTieuDe().trim());
        u.setMoTa(dto.getMoTa());
        u.setMucTieuVnd(dto.getMucTieuVnd());
        u.setDaNhanVnd(0L);
        u.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        u.setTaoLuc(OffsetDateTime.now());
        u.setCapNhatLuc(OffsetDateTime.now());

        ungHoRepository.save(u);
    }

    @Override
    public UngHoFormDTO getEditForm(Long id) {
        UngHo u = findById(id);
        if (u == null) return null;

        UngHoFormDTO dto = new UngHoFormDTO();
        dto.setUngHoId(u.getUngHoId());
        dto.setTieuDe(u.getTieuDe());
        dto.setMoTa(u.getMoTa());
        dto.setMucTieuVnd(u.getMucTieuVnd());
        dto.setTrangThai(u.getTrangThai());
        return dto;
    }

    @Transactional
    @Override
    public void update(UngHoFormDTO dto) {
        if (dto.getUngHoId() == null) throw new RuntimeException("Thiếu ID ủng hộ");
        validate(dto);

        UngHo u = findById(dto.getUngHoId());
        if (u == null) throw new RuntimeException("Không tìm thấy ủng hộ");

        u.setTieuDe(dto.getTieuDe().trim());
        u.setMoTa(dto.getMoTa());
        u.setMucTieuVnd(dto.getMucTieuVnd());
        u.setTrangThai(dto.getTrangThai() != null ? dto.getTrangThai() : true);
        u.setCapNhatLuc(OffsetDateTime.now());

        ungHoRepository.save(u);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        UngHo u = findById(id);
        if (u == null) throw new RuntimeException("Không tìm thấy ủng hộ để xóa");

        // FK giao_dich_ung_ho ON DELETE CASCADE => xóa campaign là xóa luôn giao dịch
        ungHoRepository.deleteById(id);
    }

    private void validate(UngHoFormDTO dto) {
        if (dto == null) throw new RuntimeException("Dữ liệu không hợp lệ");
        if (dto.getTieuDe() == null || dto.getTieuDe().trim().isEmpty())
            throw new RuntimeException("Tiêu đề không được để trống");
        if (dto.getMucTieuVnd() == null || dto.getMucTieuVnd() <= 0)
            throw new RuntimeException("Mục tiêu (VND) phải > 0");
    }

    @Override
    public Long dem() {
        return this.ungHoRepository.count();
    }
}

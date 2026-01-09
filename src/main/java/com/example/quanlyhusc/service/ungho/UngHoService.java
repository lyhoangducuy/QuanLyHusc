package com.example.quanlyhusc.service.ungho;

import org.springframework.data.domain.Page;

import com.example.quanlyhusc.dto.ungho.UngHoFormDTO;
import com.example.quanlyhusc.entity.ungho.UngHo;

public interface UngHoService {
    Page<UngHo> list(String keyword, int pageNo, int pageSize);
    UngHo findById(Long id);

    void create(UngHoFormDTO dto);
    UngHoFormDTO getEditForm(Long id);
    void update(UngHoFormDTO dto);

    void delete(Long id);
    Long dem();
}

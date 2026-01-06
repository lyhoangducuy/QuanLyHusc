package com.example.quanlyhusc.repository.chat;

import com.example.quanlyhusc.entity.chat.DaDocTinNhan;
import com.example.quanlyhusc.entity.chat.DaDocTinNhanId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DaDocTinNhanRepository extends JpaRepository<DaDocTinNhan, DaDocTinNhanId> {
  boolean existsById_TinNhanIdAndId_NguoiDungId(Long tinNhanId, Long nguoiDungId);
}

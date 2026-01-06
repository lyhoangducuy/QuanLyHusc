package com.example.quanlyhusc.repository.chat;

import com.example.quanlyhusc.entity.chat.CuocTroChuyen;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CuocTroChuyenRepository extends JpaRepository<CuocTroChuyen, Long> {
     @Query("""
select c
from CuocTroChuyen c
where c.loai = 'DIRECT'
  and exists (select 1 from ThanhVienTroChuyen tv1
              where tv1.cuocTroChuyen = c and tv1.nguoiDung.nguoiDungId = :a)
  and exists (select 1 from ThanhVienTroChuyen tv2
              where tv2.cuocTroChuyen = c and tv2.nguoiDung.nguoiDungId = :b)
  and 2 = (select count(tv3) from ThanhVienTroChuyen tv3 where tv3.cuocTroChuyen = c)
order by c.taoLuc desc
""")
List<CuocTroChuyen> timDirectGiuaHaiNguoi(@Param("a") Long a, @Param("b") Long b,Pageable pageable);


}

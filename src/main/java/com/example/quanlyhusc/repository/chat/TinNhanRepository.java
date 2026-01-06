package com.example.quanlyhusc.repository.chat;

import com.example.quanlyhusc.entity.chat.TinNhan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TinNhanRepository extends JpaRepository<TinNhan, Long> {

  // join fetch để Thymeleaf dùng m.nguoiGui.hoTen mà không bị LazyInitialization
    @Query("""
    select tn
    from TinNhan tn
    join fetch tn.nguoiGui nd
    where tn.cuocTroChuyen.cuocTroChuyenId = :cuocId
    order by tn.taoLuc asc
    """)
    List<TinNhan> findLichSu(@Param("cuocId") Long cuocId);

    List<TinNhan> findByCuocTroChuyen_CuocTroChuyenIdOrderByTaoLucAsc(Long cuocId);
}

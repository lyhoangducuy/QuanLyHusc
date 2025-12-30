package com.example.quanlyhusc.repository.baiviet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.quanlyhusc.entity.baiviet.BaiViet;

public interface BaiVietRepository extends JpaRepository<BaiViet,Long>{
    @Query("""
    select distinct bv
    from BaiViet bv
    left join fetch bv.tacGiaId
    left join fetch bv.dsDanhMuc bvdm
    left join fetch bvdm.danhMuc
    """)
    List<BaiViet> findAllForList();
    @Query("""
    select distinct bv
    from BaiViet bv
    left join fetch bv.dsTep
    where bv.baiVietId = :id
    """)
    BaiViet findByIdFetchDsTep(@Param("id") Long id);
    BaiViet findByGhimIsTrue();


    void deleteById(Long id);
}

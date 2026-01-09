package com.example.quanlyhusc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.quanlyhusc.entity.NguoiDung;
import com.example.quanlyhusc.entity.baiviet.DanhMucBaiViet;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;

public interface NguoiDungRepository extends JpaRepository<NguoiDung, Long> {
    NguoiDung findByTenDangNhap(String tenDangNhap);

    @EntityGraph(attributePaths = {"nguoiDungVaiTro", "nguoiDungVaiTro.vaiTro" })
    NguoiDung findByNguoiDungId(Long nguoiDungId);

    Optional<NguoiDung> findByEmail(String email);      
    @EntityGraph(attributePaths = { "nguoiDungVaiTro", "nguoiDungVaiTro.vaiTro" })
    Page<NguoiDung> findAll(Pageable pageable);

    @Query("""
                select distinct nd
                from NguoiDung nd
                join nd.nguoiDungVaiTro ndvt
                join ndvt.vaiTro vt
                where vt.maVaiTro = :ma
            """)
    List<NguoiDung> findAllByMaVaiTro(@Param("ma") String ma);

    @Query("""
                select distinct nd
                from NguoiDung nd
                join nd.nguoiDungVaiTro ndvt
                join ndvt.vaiTro vt
                where vt.maVaiTro in :dsMa
            """)
    List<NguoiDung> findAllByMaVaiTroIn(@Param("dsMa") List<String> dsMa);

    // NguoiDungRepository
    @Query("""
            select nd from NguoiDung nd
            left join fetch nd.nguoiDungVaiTro ndvt
            left join fetch ndvt.vaiTro vt
            where nd.tenDangNhap = :username
            """)
    NguoiDung findByTenDangNhapFetchRole(@Param("username") String username);

    @EntityGraph(attributePaths = { "nguoiDungVaiTro", "nguoiDungVaiTro.vaiTro" })
    @Query("""
            select nd
            from NguoiDung nd
            where lower(nd.tenDangNhap) like lower(concat('%', :keyword, '%'))
               or lower(nd.hoTen) like lower(concat('%', :keyword, '%'))
               or lower(nd.email) like lower(concat('%', :keyword, '%'))
            """)
    Page<NguoiDung> search(@Param("keyword") String keyword, Pageable pageable);
    void deleteByNguoiDungId(Long nguoiDungId);

}

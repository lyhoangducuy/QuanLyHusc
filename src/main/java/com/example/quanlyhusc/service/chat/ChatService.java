package com.example.quanlyhusc.service.chat;

import com.example.quanlyhusc.dto.chat.TinNhanDto;
import com.example.quanlyhusc.dto.chat.YeuCauGuiTinNhan;
import com.example.quanlyhusc.entity.NguoiDung;
import com.example.quanlyhusc.entity.VaiTro;
import com.example.quanlyhusc.entity.chat.*;
import com.example.quanlyhusc.repository.NguoiDungRepository;
import com.example.quanlyhusc.repository.VaiTroRepository;
import com.example.quanlyhusc.repository.chat.*;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ChatService {
    @Autowired
    private  NguoiDungRepository nguoiDungRepository;
    @Autowired
    private  CuocTroChuyenRepository cuocTroChuyenRepository;
    @Autowired
    private  ThanhVienTroChuyenRepository thanhVienTroChuyenRepository;
    @Autowired
    private  TinNhanRepository tinNhanRepo;
    @Autowired
    private  DaDocTinNhanRepository daDocRepo;
    @Autowired
    private VaiTroRepository vaiTroRepository;



  public NguoiDung layToi(Principal principal) {
    if (principal == null) throw new RuntimeException("Chưa đăng nhập");
    String username = principal.getName();

    NguoiDung me = nguoiDungRepository.findByTenDangNhap(username);
    if (me == null) throw new RuntimeException("Không tìm thấy user: " + username);
    return me;
}



  private void kiemTraThanhVien(Long cuocId, Long nguoiDungId) {
    boolean ok = this.thanhVienTroChuyenRepository.existsById_CuocTroChuyenIdAndId_NguoiDungId(cuocId, nguoiDungId);
    if (!ok) throw new RuntimeException("Bạn không thuộc cuộc trò chuyện này");
  }

  public List<TinNhan> layLichSu(Long cuocId, Principal principal) {
    NguoiDung toi = layToi(principal);
    // kiemTraThanhVien(cuocId, toi.getNguoiDungId());
    return tinNhanRepo.findLichSu(cuocId);
  }

  @Transactional
  public TinNhanDto luuTinNhan(YeuCauGuiTinNhan req, Principal principal) {
    NguoiDung toi = layToi(principal);
    kiemTraThanhVien(req.getCuocTroChuyenId(), toi.getNguoiDungId());

    CuocTroChuyen cuoc = this.cuocTroChuyenRepository.findById(req.getCuocTroChuyenId())
        .orElseThrow(() -> new RuntimeException("Không tìm thấy cuộc trò chuyện"));

    TinNhan msg = new TinNhan();
    msg.setCuocTroChuyen(cuoc);
    msg.setNguoiGui(toi);
    msg.setNoiDung(req.getNoiDung());
    msg.setLoaiTinNhan(req.getLoai() == null ? "TEXT" : req.getLoai());

    TinNhan saved = tinNhanRepo.save(msg);

    TinNhanDto dto = new TinNhanDto();
    dto.setTinNhanId(saved.getTinNhanId());
    dto.setCuocTroChuyenId(cuoc.getCuocTroChuyenId());
    dto.setNguoiGuiId(toi.getNguoiDungId());
    dto.setTenNguoiGui(toi.getHoTen());
    dto.setNoiDung(saved.getNoiDung());
    dto.setLoai(saved.getLoaiTinNhan());
    dto.setTaoLuc(saved.getTaoLuc().toString());
    return dto;
  }

  @Transactional
  public void danhDauDaDoc(Long cuocId, Principal principal) {
    NguoiDung toi = layToi(principal);
    kiemTraThanhVien(cuocId, toi.getNguoiDungId());

    List<TinNhan> ds = tinNhanRepo.findLichSu(cuocId);
    for (TinNhan m : ds) {
      if (m.getNguoiGui().getNguoiDungId().equals(toi.getNguoiDungId())) continue;

      boolean daCo = daDocRepo.existsById_TinNhanIdAndId_NguoiDungId(m.getTinNhanId(), toi.getNguoiDungId());
      if (!daCo) {
        DaDocTinNhan read = new DaDocTinNhan();
        read.setId(new DaDocTinNhanId(m.getTinNhanId(), toi.getNguoiDungId()));
        read.setTinNhan(m);
        read.setNguoiDung(toi);
        daDocRepo.save(read);
      }
    }
  }
   public List<NguoiDung> layDanhSachNguoiDeChat(Authentication auth, NguoiDung me) {
        Set<VaiTro> dsvt=this.vaiTroRepository.findByNguoiDungVaiTro(me.getNguoiDungVaiTro());
        for (VaiTro vaiTro : dsvt) {
           if (vaiTro.getMaVaiTro().equals("ADMIN")){
          return this.cuocTroChuyenRepository.timDanhSachNguoiDaCoCuocTroChuyenVoiA(me.getNguoiDungId());
        }
        }
        
        List<NguoiDung> ds=new ArrayList<>();
        ds=this.nguoiDungRepository.findAllByMaVaiTro("ADMIN");
        return ds;
    }

    @Transactional
    public Long moHoacTaoDirect(Long meId, Long targetId) {
        List<CuocTroChuyen> list = this.cuocTroChuyenRepository.timDirectGiuaHaiNguoi(meId, targetId, PageRequest.of(0, 1));
        CuocTroChuyen direct = list.isEmpty() ? null : list.get(0);

        if (direct!=null)
            return direct.getCuocTroChuyenId();
        return taoMoiDirect(meId, targetId);
    }

    @Transactional
    public Long taoMoiDirect(Long meId, Long otherId) {

    // đảm bảo 2 user tồn tại (không tồn tại thì throw luôn cho sạch)
    NguoiDung me = nguoiDungRepository.findById(meId)
            .orElseThrow(() -> new IllegalArgumentException("meId không tồn tại: " + meId));
    NguoiDung other = nguoiDungRepository.findById(otherId)
            .orElseThrow(() -> new IllegalArgumentException("otherId không tồn tại: " + otherId));

    CuocTroChuyen c = new CuocTroChuyen();
    c.setLoai("DIRECT");
    c.setTaoBoi(me);

    // 1) save trước để có cuocTroChuyenId
    c = cuocTroChuyenRepository.saveAndFlush(c);

    // 2) tạo TV #1
    ThanhVienTroChuyen tv1 = new ThanhVienTroChuyen();
    tv1.setCuocTroChuyen(c);
    tv1.setNguoiDung(me);
    tv1.setId(new ThanhVienTroChuyenId(c.getCuocTroChuyenId(), meId));
    thanhVienTroChuyenRepository.save(tv1);

    // 3) tạo TV #2
    ThanhVienTroChuyen tv2 = new ThanhVienTroChuyen();
    tv2.setCuocTroChuyen(c);
    tv2.setNguoiDung(other);
    tv2.setId(new ThanhVienTroChuyenId(c.getCuocTroChuyenId(), otherId));
    thanhVienTroChuyenRepository.save(tv2);

    return c.getCuocTroChuyenId();
    }

    public List<TinNhan> layLichSu(Long cuocId, Long meId) {
        kiemTraThanhVien(cuocId, meId);
        return tinNhanRepo.findLichSu(cuocId);
    }
}

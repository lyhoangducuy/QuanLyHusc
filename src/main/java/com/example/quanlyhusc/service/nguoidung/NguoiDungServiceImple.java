package com.example.quanlyhusc.service.nguoidung;

import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.quanlyhusc.dto.nguoidung.DangKyDTO;
import com.example.quanlyhusc.dto.nguoidung.SuaNguoiDungDTO;
import com.example.quanlyhusc.dto.nguoidung.ThemNguoiDungDTO;
import com.example.quanlyhusc.entity.NguoiDung;
import com.example.quanlyhusc.entity.NguoiDungVaiTro;
import com.example.quanlyhusc.entity.NguoiDungVaiTroId;
import com.example.quanlyhusc.entity.RefreshToken;
import com.example.quanlyhusc.entity.TokenQuenMatKhau;
import com.example.quanlyhusc.entity.VaiTro;
import com.example.quanlyhusc.entity.baiviet.DanhMucBaiViet;
import com.example.quanlyhusc.repository.NguoiDungRepository;
import com.example.quanlyhusc.repository.NguoiDungVaiTroRepository;
import com.example.quanlyhusc.repository.RefreshTokenRepository;
import com.example.quanlyhusc.repository.TokenQuenMatKhauRepository;
import com.example.quanlyhusc.repository.VaiTroRepository;

@Service
public class NguoiDungServiceImple implements NguoiDungService {
    private static final SecureRandom RAND = new SecureRandom();
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private NguoiDungRepository nguoiDungRepository;
    @Autowired
    private VaiTroRepository vaiTroRepository;
    @Autowired
    private NguoiDungVaiTroRepository nguoiDungVaiTroRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private TokenQuenMatKhauRepository tokenQuenMatKhauRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public NguoiDung findByTenDangNhap(String tenDangNhap) {
        return nguoiDungRepository.findByTenDangNhap(tenDangNhap);
    }

    @Override
    public NguoiDung findById(Long id) {
        return nguoiDungRepository.findById(id).orElse(null);
    }

    @Override
    public NguoiDung findByTenDangNhapFetchRole(String username) {
        return this.nguoiDungRepository.findByTenDangNhapFetchRole(username);
    }

    @Override
    public Page<NguoiDung> getALl(int pageNo) {
        PageRequest pageable = PageRequest.of(pageNo - 1, 6);
        return this.nguoiDungRepository.findAll(pageable);
    }

    @Override
    public Page<NguoiDung> search(String keyword, int pageNo) {
        PageRequest pageable = PageRequest.of(pageNo - 1, 6);
        return this.nguoiDungRepository.search(keyword, pageable);
    }

    @Transactional
    @Override
    public void create(ThemNguoiDungDTO themNguoiDungDTO) {
        if (nguoiDungRepository.findByTenDangNhap(themNguoiDungDTO.getTenDangNhap()) != null) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại");
        }
        if (themNguoiDungDTO.getEmail() != null
                && nguoiDungRepository.findByEmail(themNguoiDungDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã tồn tại");
        }

        NguoiDung nd = new NguoiDung();
        nd.setHoTen(themNguoiDungDTO.getHoTen());
        nd.setTenDangNhap(themNguoiDungDTO.getTenDangNhap());
        nd.setEmail(themNguoiDungDTO.getEmail());
        nd.setSoDienThoai(themNguoiDungDTO.getSoDienThoai());
        nd.setTrangThaiHoatDong(
                themNguoiDungDTO.getTrangThaiHoatDong() != null ? themNguoiDungDTO.getTrangThaiHoatDong() : true);

        // encode
        nd.setMatKhauMaHoa(this.bCryptPasswordEncoder.encode(themNguoiDungDTO.getMatKhau()));

        // save user trước để có id
        nd = this.nguoiDungRepository.save(nd);

        // gán role
        String ma = (themNguoiDungDTO.getMaVaiTro().trim());
        VaiTro role = this.vaiTroRepository.findByMaVaiTro(ma);

        NguoiDungVaiTro ndvt = new NguoiDungVaiTro();
        ndvt.setNguoiDung(nd);
        ndvt.setVaiTro(role);
        ndvt.setId(new NguoiDungVaiTroId(nd.getNguoiDungId(), role.getVaiTroId()));

        this.nguoiDungVaiTroRepository.save(ndvt);
    }

    @Override
    public NguoiDung tim(Long id) {
        return this.nguoiDungRepository.findByNguoiDungId(id);
    }

    @Override
    public SuaNguoiDungDTO getSua(Long id) {
        NguoiDung nd = this.nguoiDungRepository.findByNguoiDungId(id);
        SuaNguoiDungDTO n = new SuaNguoiDungDTO();
        n.setEmail(nd.getEmail());
        n.setHoTen(nd.getHoTen());
        n.setSoDienThoai(nd.getSoDienThoai());
        n.setTenDangNhap(nd.getTenDangNhap());
        n.setTrangThaiHoatDong(nd.getTrangThaiHoatDong());
        for (NguoiDungVaiTro v : nd.getNguoiDungVaiTro()) {
            n.setMaVaiTro(v.getVaiTro().getMaVaiTro());
            break;
        }
        return n;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        NguoiDung nd = nguoiDungRepository.findById(id).orElse(null);
        if (nd == null) {
            throw new RuntimeException("Không tìm thấy người dùng để xóa");
        }

        // 1) xóa bảng trung gian trước để tránh FK
        this.nguoiDungVaiTroRepository.deleteByNguoiDungId(id);
        this.nguoiDungRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void dangKy(DangKyDTO dangKyDTO) {
        if (nguoiDungRepository.findByTenDangNhap(dangKyDTO.getTenDangNhap()) != null) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại");
        }
        if (dangKyDTO.getEmail() != null
                && nguoiDungRepository.findByEmail(dangKyDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã tồn tại");
        }

        NguoiDung nd = new NguoiDung();
        nd.setHoTen(dangKyDTO.getHoTen());
        nd.setTenDangNhap(dangKyDTO.getTenDangNhap());
        nd.setEmail(dangKyDTO.getEmail());
        nd.setSoDienThoai(dangKyDTO.getSoDienThoai());
        nd.setTrangThaiHoatDong(false);

        // encode
        nd.setMatKhauMaHoa(this.bCryptPasswordEncoder.encode(dangKyDTO.getMatKhau()));

        // save user trước để có id
        nd = this.nguoiDungRepository.save(nd);

        // gán role
        VaiTro role = this.vaiTroRepository.findByMaVaiTro("NGUOIDUNG");

        NguoiDungVaiTro ndvt = new NguoiDungVaiTro();
        ndvt.setNguoiDung(nd);
        ndvt.setVaiTro(role);
        ndvt.setId(new NguoiDungVaiTroId(nd.getNguoiDungId(), role.getVaiTroId()));

        this.nguoiDungVaiTroRepository.save(ndvt);
    }

    @Override
    public Boolean check(DangKyDTO dto) {
        if (dto == null)
            throw new RuntimeException("Dữ liệu không hợp lệ");

        // trim cho sạch
        String hoTen = dto.getHoTen() == null ? "" : dto.getHoTen().trim();
        String tenDangNhap = dto.getTenDangNhap() == null ? "" : dto.getTenDangNhap().trim();
        String email = dto.getEmail() == null ? "" : dto.getEmail().trim();
        String soDienThoai = dto.getSoDienThoai() == null ? "" : dto.getSoDienThoai().trim();
        String matKhau = dto.getMatKhau() == null ? "" : dto.getMatKhau();
        String matKhauNhapLai = dto.getMatKhauNhapLai() == null ? "" : dto.getMatKhauNhapLai();

        // 1) bắt buộc
        if (hoTen.isEmpty())
            throw new RuntimeException("Họ tên không được để trống");
        if (tenDangNhap.isEmpty())
            throw new RuntimeException("Tên đăng nhập không được để trống");
        if (email.isEmpty())
            throw new RuntimeException("Email không được để trống");
        if (matKhau.isEmpty())
            throw new RuntimeException("Mật khẩu không được để trống");
        if (matKhauNhapLai.isEmpty())
            throw new RuntimeException("Vui lòng nhập lại mật khẩu");

        // 2) độ dài cơ bản
        if (tenDangNhap.length() < 4 || tenDangNhap.length() > 50)
            throw new RuntimeException("Tên đăng nhập phải từ 4 đến 50 ký tự");

        if (hoTen.length() < 2 || hoTen.length() > 200)
            throw new RuntimeException("Họ tên không hợp lệ");

        if (matKhau.length() < 6)
            throw new RuntimeException("Mật khẩu phải tối thiểu 6 ký tự");

        // 3) format email (đủ dùng)
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
            throw new RuntimeException("Email không đúng định dạng");

        // 4) sđt (nếu nhập thì check)
        if (!soDienThoai.isEmpty()) {
            // chỉ số, 9-11 ký tự (VN hay dùng)
            if (!soDienThoai.matches("^\\d{9,11}$"))
                throw new RuntimeException("Số điện thoại không hợp lệ (chỉ gồm 9-11 chữ số)");
        }

        // 5) confirm password
        if (!matKhau.equals(matKhauNhapLai))
            throw new RuntimeException("Mật khẩu nhập lại không khớp");

        // 6) check trùng DB (cậu có repo rồi)
        if (this.nguoiDungRepository.findByTenDangNhap(tenDangNhap) != null)
            throw new RuntimeException("Tên đăng nhập đã tồn tại");

        if (this.nguoiDungRepository.findByEmail(email).isPresent())
            throw new RuntimeException("Email đã tồn tại");

        return true;
    }

    private String genOtp6() {
        // 000000 - 999999
        int x = RAND.nextInt(1_000_000);
        return String.format("%06d", x);
    }

    @Transactional
    @Override
    public void taoOtpXacMinhVaGuiMail(String email) {
        // lấy user theo email
        NguoiDung nd = nguoiDungRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email không tồn tại"));

        if (Boolean.TRUE.equals(nd.getTrangThaiHoatDong())) {
            throw new RuntimeException("Tài khoản đã được xác minh");
        }

        // tạo OTP
        String otp = genOtp6();

        RefreshToken rt = new RefreshToken();
        rt.setNguoiDung(nd);
        rt.setToken(otp); // giữ nguyên field token
        rt.setHetHanLuc(OffsetDateTime.now().plusMinutes(15));
        rt.setThuHoiLuc(null);

        this.refreshTokenRepository.save(rt);

        String html = """
                    <h3>Mã OTP xác minh tài khoản</h3>
                    <p>Xin chào <b>%s</b>,</p>
                    <p>Mã OTP của bạn là:</p>
                    <h2 style="letter-spacing:6px;">%s</h2>
                    <p>Mã có hiệu lực trong <b>15 phút</b>.</p>
                    <p>Nếu bạn không thực hiện đăng ký, vui lòng bỏ qua email này.</p>
                """.formatted(nd.getHoTen(), otp);

        this.emailService.sendHtml(nd.getEmail(), "OTP xác minh đăng ký", html);
    }

    @Transactional
    @Override
    public void xacMinhEmailBangOtp(String email, String otp) {
        if (email == null || email.trim().isEmpty())
            throw new RuntimeException("Thiếu email");
        if (otp == null || otp.trim().isEmpty())
            throw new RuntimeException("Thiếu OTP");

        email = email.trim();
        otp = otp.trim();

        if (!otp.matches("^\\d{6}$")) {
            throw new RuntimeException("OTP phải gồm 6 chữ số");
        }

        RefreshToken rt = this.refreshTokenRepository
                .findFirstByNguoiDung_EmailAndTokenAndThuHoiLucIsNullOrderByTaoLucDesc(email, otp)
                .orElseThrow(() -> new RuntimeException("OTP không đúng"));

        if (OffsetDateTime.now().isAfter(rt.getHetHanLuc())) {
            throw new RuntimeException("OTP đã hết hạn");
        }

        // kích hoạt user
        NguoiDung nd = rt.getNguoiDung();
        nd.setTrangThaiHoatDong(true);
        this.nguoiDungRepository.save(nd);

        // thu hồi OTP
        rt.setThuHoiLuc(OffsetDateTime.now());
        this.refreshTokenRepository.save(rt);
    }

    @Transactional
    @Override
    public void guiLinkQuenMatKhau(String email, String baseUrl) {
        if (email == null || email.trim().isEmpty())
            throw new RuntimeException("Vui lòng nhập email");

        email = email.trim();

        NguoiDung nd = this.nguoiDungRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email không tồn tại trong hệ thống"));

        // tạo token link
        String token = UUID.randomUUID().toString() + UUID.randomUUID().toString();

        TokenQuenMatKhau t = new TokenQuenMatKhau();
        t.setNguoiDung(nd);
        t.setToken(token);
        t.setHetHanLuc(OffsetDateTime.now().plusMinutes(15));
        t.setDaDungLuc(null);
        this.tokenQuenMatKhauRepository.save(t);

        String link = baseUrl + "/datLaiMatKhau?token=" + token;

        String html = """
                    <h3>Đặt lại mật khẩu</h3>
                    <p>Xin chào <b>%s</b>,</p>
                    <p>Bạn vừa yêu cầu đặt lại mật khẩu. Vui lòng bấm vào link bên dưới:</p>
                    <p><a href="%s">Đặt lại mật khẩu</a></p>
                    <p>Link có hiệu lực trong <b>15 phút</b>.</p>
                    <p>Nếu bạn không thực hiện yêu cầu này, vui lòng bỏ qua email.</p>
                """.formatted(nd.getHoTen(), link);

        this.emailService.sendHtml(nd.getEmail(), "Đặt lại mật khẩu", html);
    }
    @Transactional
@Override
public void datLaiMatKhau(String token, String mkMoi, String mkNhapLai) {
    if (token == null || token.trim().isEmpty()) 
        throw new RuntimeException("Token không hợp lệ");
    if (mkMoi == null || mkMoi.trim().isEmpty()) 
        throw new RuntimeException("Mật khẩu mới không được để trống");
    if (mkNhapLai == null || mkNhapLai.trim().isEmpty()) 
        throw new RuntimeException("Vui lòng nhập lại mật khẩu");
    if (!mkMoi.equals(mkNhapLai)) 
        throw new RuntimeException("Mật khẩu nhập lại không khớp");
    if (mkMoi.length() < 6) 
        throw new RuntimeException("Mật khẩu phải tối thiểu 6 ký tự");

    TokenQuenMatKhau t = this.tokenQuenMatKhauRepository.findByToken(token.trim())
        .orElseThrow(() -> new RuntimeException("Link đặt lại mật khẩu không hợp lệ"));

    if (t.getDaDungLuc() != null) throw new RuntimeException("Link này đã được sử dụng");
    if (OffsetDateTime.now().isAfter(t.getHetHanLuc())) throw new RuntimeException("Link đã hết hạn");

    NguoiDung nd = t.getNguoiDung();
    nd.setMatKhauMaHoa(bCryptPasswordEncoder.encode(mkMoi));
    this.nguoiDungRepository.save(nd);

    t.setDaDungLuc(OffsetDateTime.now());
    this.tokenQuenMatKhauRepository.save(t);
}

}

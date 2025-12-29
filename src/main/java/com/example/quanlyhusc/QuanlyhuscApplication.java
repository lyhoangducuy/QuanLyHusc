package com.example.quanlyhusc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.quanlyhusc.entity.NguoiDung;
import com.example.quanlyhusc.repository.NguoiDungRepository;

@SpringBootApplication
public class QuanlyhuscApplication {
@Bean
CommandLineRunner seed(NguoiDungRepository repo, PasswordEncoder encoder) {
    return args -> {
        if (repo.findByTenDangNhap("test") == null) {
            NguoiDung u = new NguoiDung();
            u.setTenDangNhap("test");
            u.setHoTen("Tài khoản test");
            u.setMatKhauMaHoa(encoder.encode("123456"));
            u.setTrangThaiHoatDong(true);
            repo.save(u);
        }
    };
}

	public static void main(String[] args) {
		
		SpringApplication.run(QuanlyhuscApplication.class, args);
		
	}

}

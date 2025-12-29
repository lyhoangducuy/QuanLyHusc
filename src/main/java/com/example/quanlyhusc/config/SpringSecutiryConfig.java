package com.example.quanlyhusc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.quanlyhusc.service.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SpringSecutiryConfig {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
        http.csrf(csrf->csrf.disable()).authorizeHttpRequests(auth->
            auth.requestMatchers("/**","/login").permitAll()
            .requestMatchers("/admin/**","/**").hasAnyAuthority("ADMIN")
            .anyRequest().authenticated()).formLogin(login->login.loginPage("/login").loginProcessingUrl("/login")
            .usernameParameter("tenDangNhap").passwordParameter("matKhau").defaultSuccessUrl("/admin/",true))
            .logout(logout->logout.logoutUrl("/logout").logoutSuccessUrl("/login"));
        return http.build();
    }
    @Bean
    WebSecurityCustomizer webSecurityCustomizer(){
        return (web)->web.ignoring().requestMatchers("/static/**","/assets/**");
    }
}

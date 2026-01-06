package com.example.quanlyhusc.controller.chat;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.quanlyhusc.entity.CustomUserDetails;
import com.example.quanlyhusc.entity.NguoiDung;
import com.example.quanlyhusc.entity.chat.TinNhan;
import com.example.quanlyhusc.service.chat.ChatService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatPageController {
    @Autowired
    private ChatService chatService;

    // 1) Vào /chat -> hiện danh sách người để chat
    @GetMapping
    public String danhSachChat(Authentication auth, Principal principal, Model model) {
        NguoiDung me = chatService.layToi(principal);
        List<NguoiDung> ds = chatService.layDanhSachNguoiDeChat(auth, me);
        model.addAttribute("me", me);
        model.addAttribute("dsNguoi", ds);
        return "chat/danh-sach";
    }

    // 2) Click 1 người -> mở hoặc tạo DIRECT -> redirect sang phòng
    @GetMapping("/direct/{targetId}")
    public String moDirect(@AuthenticationPrincipal CustomUserDetails user, @PathVariable Long targetId) {
        Long cuocId = chatService.moHoacTaoDirect(user.getNguoiDungId(), targetId);
        return "redirect:/chat/" + cuocId;
    }

    // 3) Mở phòng chat
    @GetMapping("/{cuocId}")
    public String moPhong(@PathVariable Long cuocId, Principal principal, Model model) {
        NguoiDung me = chatService.layToi(principal);
        List<TinNhan> lichSu = this.chatService.layLichSu(cuocId, me.getNguoiDungId());

        model.addAttribute("me", me);
        model.addAttribute("cuocId", cuocId);
        model.addAttribute("lichSu", lichSu);
        return "chat/phong-chat";
    }
}

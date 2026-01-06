package com.example.quanlyhusc.controller.chat;

import com.example.quanlyhusc.dto.chat.TinNhanDto;
import com.example.quanlyhusc.dto.chat.YeuCauGuiTinNhan;
import com.example.quanlyhusc.service.chat.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatWsController {

  private final SimpMessagingTemplate template;
  private final ChatService chatService;

  public ChatWsController(SimpMessagingTemplate template, ChatService chatService) {
    this.template = template;
    this.chatService = chatService;
  }

  // Client gửi: /app/chat.gui
  @MessageMapping("/chat.gui")
  public void gui(YeuCauGuiTinNhan req, Principal principal) {
    TinNhanDto saved = chatService.luuTinNhan(req, principal);

    // Room của cuộc trò chuyện: /topic/chat.{id}
    template.convertAndSend("/topic/chat." + req.getCuocTroChuyenId(), saved);
  }
}

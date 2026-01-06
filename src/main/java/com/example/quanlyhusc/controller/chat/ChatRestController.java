package com.example.quanlyhusc.controller.chat;

import com.example.quanlyhusc.service.chat.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/chat")
public class ChatRestController {

  private final ChatService chatService;

  public ChatRestController(ChatService chatService) {
    this.chatService = chatService;
  }

  @PostMapping("/{id}/da-doc")
  public ResponseEntity<?> danhDauDaDoc(@PathVariable Long id, Principal principal) {
    chatService.danhDauDaDoc(id, principal);
    return ResponseEntity.ok().build();
  }
}
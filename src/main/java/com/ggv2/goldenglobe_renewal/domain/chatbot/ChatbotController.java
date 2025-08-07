package com.ggv2.goldenglobe_renewal.domain.chatbot;

import com.ggv2.goldenglobe_renewal.domain.chatbot.chatbotDTO.ChatRequest;
import com.ggv2.goldenglobe_renewal.domain.chatbot.chatbotDTO.ChatResponse;
import com.ggv2.goldenglobe_renewal.global.auth.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/travels/{travelListId}/chat")
@RequiredArgsConstructor
public class ChatbotController {
  private final ChatbotService chatbotService;

  @PostMapping
  public ResponseEntity<ChatResponse> chat(
      @PathVariable Long travelListId,
      @AuthenticationPrincipal CustomUser customUser,
      @RequestBody ChatRequest request) {

    ChatResponse response = chatbotService.getChatbotResponse(travelListId, customUser.getUser().getId(), request);
    return ResponseEntity.ok(response);
  }
}

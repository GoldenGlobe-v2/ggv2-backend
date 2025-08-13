package com.ggv2.goldenglobe_renewal.domain.chatbot;

import com.ggv2.goldenglobe_renewal.domain.chatbot.chatbotDTO.ChatRequest;
import com.ggv2.goldenglobe_renewal.domain.chatbot.chatbotDTO.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatbotService {

  private final RestTemplate restTemplate;
  // ... Chatbot, ChatbotLog, TravelList Repository 주입 필요 ...

  public ChatResponse getChatbotResponse(Long travelListId, Long userId, ChatRequest request) {
    // 1. 여행 목록 조회 및 사용자 권한 확인
    // ...

    // 2. 해당 여행의 PDF 목록 등 AI에게 필요한 컨텍스트 정보 수집
    // ...

    // 3. Flask AI 서버에 요청 보내기 (URL은 실제 AI 서버 주소로 변경 필요)
    // String flaskApiUrl = "http://localhost:5001/chat";
    // ResponseEntity<ChatResponse> responseEntity = restTemplate.postForEntity(flaskApiUrl, requestWithContext, ChatResponse.class);
    // String aiAnswer = responseEntity.getBody().answer();
    String aiAnswer = "이것은 AI 서버로부터 받은 임시 답변입니다."; // 임시 답변

    // 4. 대화 기록(ChatbotLog) DB에 저장
    // ...

    return new ChatResponse(aiAnswer);
  }
}
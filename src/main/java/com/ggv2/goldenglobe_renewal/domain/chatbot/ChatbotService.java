package com.ggv2.goldenglobe_renewal.domain.chatbot;

import com.ggv2.goldenglobe_renewal.domain.chatbot.chatbotDTO.AIChatRequest;
import com.ggv2.goldenglobe_renewal.domain.chatbot.chatbotDTO.ChatRequest;
import com.ggv2.goldenglobe_renewal.domain.chatbot.chatbotDTO.ChatResponse;
import com.ggv2.goldenglobe_renewal.domain.chatLog.ChatLog;
import com.ggv2.goldenglobe_renewal.domain.chatLog.ChatLogRepository;
import com.ggv2.goldenglobe_renewal.domain.travelList.TravelList;
import com.ggv2.goldenglobe_renewal.domain.travelList.TravelListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChatbotService {

  private final RestTemplate restTemplate;
  private final TravelListRepository travelListRepository;
  private final ChatLogRepository chatLogRepository;

  @Value("${ai.server.url}")
  private String aiServerUrl;

  /** FastAPI /chat 호출 + 대화 로그 저장 */
  public ChatResponse getChatbotResponse(Long travelListId, Long userId, ChatRequest request) {
    // 1) 여행목록 존재 + 소유자 검증
    TravelList tl = travelListRepository.findById(travelListId)
        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "여행 목록을 찾을 수 없습니다: " + travelListId));
    log.debug("chat tlId={}, reqUserId={}, ownerId={}", travelListId, userId, tl.getUser().getId());
    if (!tl.getUser().getId().equals(userId)) {
      throw new ResponseStatusException(FORBIDDEN, "이 여행 목록에 접근 권한이 없습니다.");
    }

    // 2) 사용자 질문 로그 저장
    chatLogRepository.save(ChatLog.user(tl, request.question()));

    // 3) FastAPI 호출
    String answer;
    try {
      AIChatRequest aiReq = new AIChatRequest(travelListId, request.question());
      ResponseEntity<ChatResponse> entity =
          restTemplate.postForEntity(aiServerUrl + "/chat", aiReq, ChatResponse.class);

      ChatResponse body = entity.getBody();
      answer = (body != null && body.answer() != null)
          ? body.answer()
          : "답변을 생성하는 중 오류가 발생했습니다.";

    } catch (RestClientException e) {
      // 네트워크/5xx 등 FastAPI 호출 실패
      chatLogRepository.save(ChatLog.bot(tl, "AI 서버 호출 실패"));
      throw new ResponseStatusException(BAD_GATEWAY, "AI server call failed", e);
    }

    // 4) 봇 답변 로그 저장
    chatLogRepository.save(ChatLog.bot(tl, answer));

    // 5) 반환
    return new ChatResponse(answer);
  }
}

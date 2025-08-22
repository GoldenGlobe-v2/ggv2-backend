package com.ggv2.goldenglobe_renewal.domain.chatLog;

import com.ggv2.goldenglobe_renewal.domain.travelList.TravelList;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ChatLog {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "travel_list_id", nullable = false)
  private TravelList travelList;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  private Role role; // USER / BOT

  @Column(nullable = false, columnDefinition = "text")
  private String message;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  public enum Role { USER, BOT }

  public static ChatLog user(TravelList tl, String msg) {
    return ChatLog.builder()
        .travelList(tl).role(Role.USER).message(msg).createdAt(LocalDateTime.now()).build();
  }
  public static ChatLog bot(TravelList tl, String msg) {
    return ChatLog.builder()
        .travelList(tl).role(Role.BOT).message(msg).createdAt(LocalDateTime.now()).build();
  }
}

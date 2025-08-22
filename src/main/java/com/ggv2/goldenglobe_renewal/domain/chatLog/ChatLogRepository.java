package com.ggv2.goldenglobe_renewal.domain.chatLog;

import com.ggv2.goldenglobe_renewal.domain.travelList.TravelList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatLogRepository extends JpaRepository<ChatLog, Long> {
  List<ChatLog> findByTravelList(TravelList travelList);
}

package com.ggv2.goldenglobe_renewal.domain.chatbot.chatbotDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AIChatRequest(
    @JsonProperty("travel_list_id") Long travelListId,
    String question
) {
}

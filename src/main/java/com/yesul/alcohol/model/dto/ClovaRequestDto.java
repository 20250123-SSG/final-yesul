package com.yesul.alcohol.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClovaRequestDto {

    private String query;
    private boolean tokenStream;
    private List<ChatMessage> chatHistory;
    private RequestOverride requestOverride;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatMessage {
        private String role;
        private String content;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestOverride {
        private BaseOperation baseOperation;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BaseOperation {
        private Map<String, String> query;
    }
}
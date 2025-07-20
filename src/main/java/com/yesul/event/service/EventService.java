package com.yesul.event.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yesul.event.model.dto.FormRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class EventService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public String createGoogleForm(FormRequestDto formRequest, String accessToken) {
        String url = "https://forms.googleapis.com/v1/forms";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // JSON 본문 구성
        ObjectNode infoNode = objectMapper.createObjectNode();
        infoNode.put("title", formRequest.getTitle());
        infoNode.put("documentTitle", formRequest.getDocumentTitle());
        infoNode.put("description", formRequest.getDescription());

        ObjectNode bodyNode = objectMapper.createObjectNode();
        bodyNode.set("info", infoNode);

        try {
            String bodyJson = objectMapper.writeValueAsString(bodyNode);
            HttpEntity<String> entity = new HttpEntity<>(bodyJson, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            return extractFormEditUrl(response.getBody());
        } catch (Exception e) {
            return "폼 생성 실패: " + e.getMessage();
        }
    }

    private String extractFormEditUrl(String responseBody) {
        try {
            JsonNode root = objectMapper.readTree(responseBody);
            return root.get("responderUri").asText(); // 생성된 응답 URL
        } catch (Exception e) {
            return "폼 파싱 실패";
        }
    }
}
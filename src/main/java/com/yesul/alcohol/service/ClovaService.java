package com.yesul.alcohol.service;

import com.yesul.alcohol.model.dto.ClovaRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class ClovaService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String alcoholSkillTrainerAPI(ClovaRequestDto dto) {
        String url = "https://clovastudio.stream.ntruss.com/testapp/v1/skillsets/fv46jc9v/versions/14/final-answer";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer <api-key>");
        headers.set("X-NCP-CLOVASTUDIO-REQUEST-ID", UUID.randomUUID().toString());
        headers.setAccept(List.of(MediaType.TEXT_EVENT_STREAM));

        HttpEntity<ClovaRequestDto> entity = new HttpEntity<>(dto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, String.class
        );

        return response.getBody();
    }

    public String travelAPI(ClovaRequestDto dto) {

        return "여행지";
    }
}

package com.yesul.alcohol.controller;

import com.yesul.alcohol.model.dto.ClovaRequestDto;
import com.yesul.alcohol.service.ClovaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clova")
public class ClovaController {

    private final ClovaService clovaService;

    @PostMapping("/ask")
    public ResponseEntity<String> askClova(@RequestBody ClovaRequestDto requestDto) {
        String response = clovaService.alcoholSkillTrainerAPI(requestDto);
        return ResponseEntity.ok(response);
    }
}
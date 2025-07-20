package com.yesul.event.controller;

import com.yesul.event.model.dto.EventFormRequestDto;
import com.yesul.event.model.dto.FormRequestDto;
import com.yesul.event.model.dto.QuestionRequestDto;
import com.yesul.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/event")
public class EventFormController {

    private final EventService eventService;
    private final OAuth2AuthorizedClientService clientService;

    @PostMapping("/list")
    public ResponseEntity<String> receiveFormResponses(@RequestBody EventFormRequestDto request) {
        System.out.println("📨 폼 ID: " + request.getForm_id());
        System.out.println("📨 폼 제목: " + request.getForm_title());

        for (QuestionRequestDto question : request.getResults()) {
            System.out.println(question.getType());
            System.out.println("📝 질문: " + question.getTitle());
            System.out.println("➡️ 응답: " + question.getResponse());
        }

        return ResponseEntity.ok("✅ 데이터 수신 성공");
    }

    @GetMapping("/create")
    public String showFormCreatePage() {
        return "form/create"; // 위의 HTML 렌더링
    }

    @PostMapping("/create")
    public String createForm(@ModelAttribute FormRequestDto formRequest,
                             @AuthenticationPrincipal OAuth2AuthenticationToken authToken,
                             Model model) {

        String registrationId = authToken.getAuthorizedClientRegistrationId();
        if (!"google".equals(registrationId)) {
            return "redirect:/login";
        }

        OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
                registrationId,
                authToken.getName()
        );

        String accessToken = client.getAccessToken().getTokenValue();
        String formUrl = eventService.createGoogleForm(formRequest, accessToken);

        model.addAttribute("formUrl", formUrl);
        return "form/success";
    }

}
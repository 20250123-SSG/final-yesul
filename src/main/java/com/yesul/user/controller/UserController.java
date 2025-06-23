package com.yesul.user.controller;

import com.yesul.exception.handler.EntityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.yesul.user.model.dto.UserRegisterDto;
import com.yesul.user.service.UserService;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/regist")
    public String registForm(Model model) {
        model.addAttribute("userRegisterDto", new UserRegisterDto());
        return "user/regist";
    }

    @PostMapping("/regist-process")
    @ResponseBody
    public String registProcess(@RequestBody UserRegisterDto userRegisterDto) {
        try {
            userService.registerUser(userRegisterDto);
            log.info("회원가입 요청 성공, 이메일 발송 대기: {}", userRegisterDto.getEmail());
            return "{\"success\": true, \"message\": \"회원가입을 위한 인증 메일을 발송했습니다. 이메일을 확인하여 인증을 완료해주세요.\"}";
        } catch (IllegalArgumentException e) {
            log.warn("회원가입 실패 (입력 오류): {}", e.getMessage());
            return "{\"success\": false, \"message\": \"" + e.getMessage() + "\"}";
        } catch (RuntimeException e) {
            log.error("회원가입 중 오류 발생: {}", e.getMessage(), e);
            return "{\"success\": false, \"message\": \"회원가입 중 시스템 오류가 발생했습니다. 다시 시도해주세요.\"}";
        }
    }

    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam String email, @RequestParam String token, RedirectAttributes redirectAttributes) {
        try {
            boolean verified = userService.verifyEmail(email, token);
            if (verified) {
                redirectAttributes.addFlashAttribute("message", "이메일 인증이 완료되었습니다! 이제 로그인해주세요.");
                log.info("이메일 인증 성공: {}", email);
                return "redirect:/user/login";
            } else {
                redirectAttributes.addFlashAttribute("error", "이메일 인증에 실패했습니다. 링크가 유효하지 않거나 만료되어 새로운 인증 메일이 발송되었습니다. 다시 이메일을 확인해주세요.");
                log.warn("이메일 인증 실패 (토큰 문제): {}", email);
                return "redirect:/user/regist";
            }
        } catch (EntityNotFoundException e) {
            log.error("이메일 인증 실패: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "이메일 인증에 실패했습니다. 사용자 정보를 찾을 수 없습니다.");
            return "redirect:/user/regist";
        } catch (Exception e) {
            log.error("이메일 인증 중 예외 발생: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", "이메일 인증 중 오류가 발생했습니다. 다시 시도해주세요.");
            return "redirect:/user/regist";
        }
    }
}
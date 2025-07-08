package com.yesul.login.controller;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yesul.user.model.entity.User;
import com.yesul.user.model.dto.UserPasswordResetDto;
import com.yesul.user.service.UserService;


@Controller
@RequiredArgsConstructor
@Slf4j
public class UserLoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginForm() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 로그인 확인
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/user/profile";
        }
        // 로그인 상태가 아닐 시
        return "/login/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login/login";
    }

    // 비밀번호 초기화 페이지 이동
    @GetMapping("/reset-password")
    public String showRequestForm(Model model) {
        model.addAttribute("resetDto", new UserPasswordResetDto());
        return "login/reset-password";
    }

    // 비밀번호 초기화
    @PostMapping("/reset-password")
    public String handleRequest(
            @Validated @ModelAttribute("resetDto") UserPasswordResetDto dto,
            BindingResult br,
            RedirectAttributes ra) {

        if (br.hasErrors()) {
            return "login/reset-password";
        }

        Optional<User> checkUser = userService.findUserByEmail(dto.getEmail());

        if (checkUser.isEmpty()) {
            ra.addFlashAttribute("error", "등록되지 않은 메일입니다");
            return "redirect:/login/reset-password";
        }

        User user = checkUser.get();

        if (user.getType() == '2') {
            userService.resendSignUpVerification(dto.getEmail());
            ra.addFlashAttribute("message", "가입 인증 메일을 재발송했습니다.");
            return "redirect:/user/password-request-complete";
        } else if (user.getType() == '1') {
            userService.resendPasswordResetLink(dto.getEmail());
            ra.addFlashAttribute("message", "비밀번호 재설정 메일을 발송했습니다.");
            return "redirect:/user/password-request-complete";
        } else {
            ra.addFlashAttribute("error", "현재 상태에서 요청을 처리할 수 없습니다.");
            return "redirect:/reset-password";
        }
    }

    @GetMapping("/password-request-complete")
    public String showRequestComplete() {
        return "user/password-request-complete";
    }
}
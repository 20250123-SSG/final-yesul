package com.yesul.login.handler;

import com.yesul.admin.model.entity.Admin;
import com.yesul.login.service.AdminOtpService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class AdminLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler { // Spring Security의 기본 로그인 성공 핸들러 extend

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        response.sendRedirect("/admin/otp");
    }
}

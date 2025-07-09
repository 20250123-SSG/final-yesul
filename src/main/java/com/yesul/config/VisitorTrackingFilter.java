package com.yesul.config;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class VisitorTrackingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String visitorId = getOrCreateVisitorId(req, res);

        // (추후 여기에 Redis 저장 로직 추가)

        chain.doFilter(request, response);
    }

    private String getOrCreateVisitorId(HttpServletRequest req, HttpServletResponse res) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("visitorId".equals(c.getName())) {
                    return c.getValue();
                }
            }
        }
        String uuid = UUID.randomUUID().toString();
        Cookie newCookie = new Cookie("visitorId", uuid);
        newCookie.setMaxAge(60 * 60 * 24 * 365); // 1년
        newCookie.setPath("/");
        res.addCookie(newCookie);
        return uuid;
    }
}
package com.yesul.exception;

import com.yesul.exception.handler.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public String handleEntityNotFound(UserNotFoundException e, HttpServletRequest request) {
        // 로그 남기기
        log.warn("[EntityNotFoundException] {} {} - {}", request.getMethod(), request.getRequestURI(), e.getMessage());
        return "404.html";
    }



}

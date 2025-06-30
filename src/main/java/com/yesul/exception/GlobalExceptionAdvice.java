package com.yesul.exception;

import com.yesul.exception.handler.UpdateFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@ControllerAdvice
public class GlobalExceptionAdvice {
//
//    @ExceptionHandler(EntityNotFoundException.class)
//    public String handleEntityNotFound(EntityNotFoundException e,HttpServletRequest request) {
//        // 로그 남기기
//        log.warn("[EntityNotFoundException] {} {} - {}", request.getMethod(), request.getRequestURI(), e.getMessage());
//        return "404.html";
//    }

    @ExceptionHandler(UpdateFailedException.class)
    public String handleUserStatusUpdateFailed(UpdateFailedException e,
                                               RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());
        return "redirect:/admin/userInfo/list";
    }



}

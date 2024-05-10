package com.artcorp.artsync.exception.domain;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import java.io.IOException;

import static com.artcorp.artsync.constant.SecurityConstant.ACCESS_DENIED_MESSAGE;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws ServletException, IOException {
        final FlashMap flashMap = new FlashMap();
        flashMap.put("warn",ACCESS_DENIED_MESSAGE);
        final FlashMapManager flashMapManager = new SessionFlashMapManager();
        flashMapManager.saveOutputFlashMap(flashMap,request,response);
        response.sendRedirect("/authentification");
    }
}

package com.artcorp.artsync.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import java.io.IOException;

import static com.artcorp.artsync.constant.ExceptionConstant.UNAUTHORIZED;
import static com.artcorp.artsync.constant.ExceptionConstant.WARN;

public class CustomAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        boolean isApiRequest = request.getRequestURI().startsWith("/api");
        if (isApiRequest) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            String responseBody = "{\"error\": \"" + UNAUTHORIZED+ "\"}";
            response.getWriter().write(responseBody);

        } else {
            final FlashMap flashMap = new FlashMap();
            flashMap.put(WARN,UNAUTHORIZED);
            final FlashMapManager flashMapManager = new SessionFlashMapManager();
            flashMapManager.saveOutputFlashMap(flashMap,request,response);
            response.sendRedirect("/authentification");
        }
    }
}

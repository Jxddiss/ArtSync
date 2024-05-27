package com.artcorp.artsync.exception;

import com.artcorp.artsync.exception.domain.FileFormatException;
import com.artcorp.artsync.exception.domain.NotConnectedException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

import static com.artcorp.artsync.constant.ExceptionConstant.*;

@ControllerAdvice
public class ExceptionHandling {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(BadCredentialsException.class)
    public String mauvaisIdentifiants(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute(WARN, MAUVAIS_IDENTIFIANTS);
        return "redirect:/authentification";
    }

    @ExceptionHandler(NotConnectedException.class)
    public String nonConnected(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute(WARN, VEUILLER_VOUS_CONNECTER);
        return "redirect:/authentification";
    }

    @ExceptionHandler(FileFormatException.class)
    public String fileFormatExceptionHandler(RedirectAttributes redirectAttributes, FileFormatException fileFormatException){
        redirectAttributes.addFlashAttribute(ERROR, FICHIER_NON_PRIS_EN_CHARGE);
        return "redirect:"+fileFormatException.getMessage();
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String maxUploadSizeExceededExceptionExceptionHandler(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute(ERROR, FICHIER_TROP_LOURD);
        return "redirect:/feed";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void accessDeniedHandler(RedirectAttributes redirectAttributes,
                                      HttpServletRequest request,
                                      HttpServletResponse response,
                                      AccessDeniedException ex) throws IOException {
        boolean isApiRequest = request.getRequestURI().contains("/api");
        if (isApiRequest) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            String responseBody = "{\"error\": \"" + ex.getMessage() + "\"}";
            response.getWriter().write(responseBody);

        } else {
            redirectAttributes.addFlashAttribute(ERROR, VEUILLER_VOUS_CONNECTER);
            response.sendRedirect(request.getContextPath() + "/authentification");
        }
    }

    @ExceptionHandler(JWTVerificationException.class)
    public String decodeException(HttpServletRequest request,
                                  HttpServletResponse response){
        HttpSession session = request.getSession();
        session.invalidate();
        SecurityContextHolder.clearContext();
        Cookie jwtCookie = new Cookie("jwt","");
        jwtCookie.setHttpOnly(true);
        response.addCookie(jwtCookie);
        return "redirect:/authentification";
    }

    @ExceptionHandler(Exception.class)
    public String exception(RedirectAttributes redirectAttributes, Exception ex){
        LOGGER.error(ex.getMessage());
        redirectAttributes.addFlashAttribute(ERROR, UNE_ERREUR_EST_SURVENUE);
        return "redirect:/";
    }
}

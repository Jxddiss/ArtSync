package com.artcorp.artsync.exception;

import com.artcorp.artsync.dto.HttpResponse;
import com.artcorp.artsync.exception.domain.FileFormatException;
import com.artcorp.artsync.exception.domain.NotConnectedException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.servlet.support.SessionFlashMapManager;

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

    @ExceptionHandler(DisabledException.class)
    public String compteDesactive(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute(WARN, COMPTE_DESACTIVE);
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
            String responseBody = createApiHttpResponse(HttpStatus.FORBIDDEN,UNAUTHORIZED);
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

    @ExceptionHandler(NoResourceFoundException.class)
    public void noRessourceHandler(HttpServletRequest request,
                          HttpServletResponse response,
                          Exception ex) throws IOException {
        LOGGER.error(ex.getMessage());

        boolean isApiRequest = request.getRequestURI().contains("/api");

        if (isApiRequest) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setContentType("application/json");
            String responseBody = createApiHttpResponse(HttpStatus.NOT_FOUND,NOT_FOUND);
            response.getWriter().write(responseBody);
        }else{
            final FlashMap flashMap = new FlashMap();
            flashMap.put(ERROR,NOT_FOUND);
            final FlashMapManager flashMapManager = new SessionFlashMapManager();
            flashMapManager.saveOutputFlashMap(flashMap,request,response);
            response.sendRedirect(request.getContextPath() + "/");
        }
    }


    @ExceptionHandler(Exception.class)
    public void exception(HttpServletRequest request,
                            HttpServletResponse response,
                            Exception ex) throws IOException {
        LOGGER.error(ex.getMessage());

        boolean isApiRequest = request.getRequestURI().contains("/api");

        if (isApiRequest) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json");
            String responseBody = createApiHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR,UNE_ERREUR_EST_SURVENUE);
            response.getWriter().write(responseBody);
        }else{
            final FlashMap flashMap = new FlashMap();
            flashMap.put(ERROR,UNE_ERREUR_EST_SURVENUE);
            final FlashMapManager flashMapManager = new SessionFlashMapManager();
            flashMapManager.saveOutputFlashMap(flashMap,request,response);
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

    @ExceptionHandler(IOException.class)
    private ResponseEntity<HttpResponse> handleIOException(IOException ioException){
        LOGGER.error(ioException.getMessage());
        HttpResponse httpResponse = new HttpResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                UNE_ERREUR_EST_SURVENUE);
        return new ResponseEntity<>(httpResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String createApiHttpResponse(HttpStatus httpStatus, String message) throws JsonProcessingException {
        HttpResponse httpResponse = new HttpResponse(httpStatus.value(),
                httpStatus, httpStatus.getReasonPhrase(), message);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(httpResponse);
    }
}

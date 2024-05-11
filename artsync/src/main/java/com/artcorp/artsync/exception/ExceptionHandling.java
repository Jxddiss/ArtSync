package com.artcorp.artsync.exception;

import com.artcorp.artsync.exception.domain.FileFormatException;
import com.artcorp.artsync.exception.domain.MauvaisIdentifiantException;
import com.artcorp.artsync.exception.domain.NotConnectedException;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.AccessDeniedException;
import java.util.Date;

import static com.artcorp.artsync.constant.SecurityConstant.EXPIRATION_TIME;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class ExceptionHandling {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    public static final String UNE_ERREUR_EST_SURVENUE = "Une erreur est survenue";
    public static final String VEUILLEZ_VOUS_RECONNECTER = "Veuillez vous reconnecter";
    public static final String FICHIER_TROP_LOURD = "Fichier trop lourd";
    public static final String FICHIER_NON_PRIS_EN_CHARGE = "Fichier non pris en charge";
    public static final String MAUVAIS_IDENTIFIANTS = "Mauvais identifiants";
    public static final String VEUILLER_VOUS_CONNECTER = "Veuiller vous connecter";
    public static final String WARN = "warn";
    public static final String ERROR = "error";

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
    public String accessDeniedHandler(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute(ERROR, VEUILLER_VOUS_CONNECTER);
        return "redirect:/authentification";
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

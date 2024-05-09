package com.artcorp.artsync.exception;

import com.artcorp.artsync.exception.domain.FileFormatException;
import com.artcorp.artsync.exception.domain.MauvaisIdentifiantException;
import com.artcorp.artsync.exception.domain.NotConnectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//@ControllerAdvice
public class ExceptionHandling {
    /*public static final String UNE_ERREUR_EST_SURVENUE = "Une erreur est survenue";
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    public static final String MAUVAIS_IDENTIFIANTS = "Mauvais identifiants";
    public static final String VEUILLER_VOUS_CONNECTER = "Veuiller vous connecter";
    public static final String WARN = "warn";
    public static final String ERROR = "error";

    @ExceptionHandler(MauvaisIdentifiantException.class)
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
        redirectAttributes.addFlashAttribute("error", "Fichier non pris en charge");
        return "redirect:"+fileFormatException.getMessage();
    }

    @ExceptionHandler(Exception.class)
    public String exception(RedirectAttributes redirectAttributes, Exception ex){
        LOGGER.error(ex.getMessage());
        redirectAttributes.addFlashAttribute(ERROR, UNE_ERREUR_EST_SURVENUE);
        return "redirect:/";
    }*/
}

package com.artcorp.artsync.exception;

import com.artcorp.artsync.exception.domain.MauvaisIdentifiantException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(MauvaisIdentifiantException.class)
    public String mauvaisIdentifiants(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("error", "Mauvais identifiants");
        return "redirect:/authentification";
    }
}

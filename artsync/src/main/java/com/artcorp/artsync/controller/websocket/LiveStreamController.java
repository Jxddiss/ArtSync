package com.artcorp.artsync.controller.websocket;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LiveStreamController {

    @GetMapping("/live")
    public String live(){
        return "utilisateur/live-stream";
    }

    @GetMapping("/live/start")
    public String startLive(Model model,
                            HttpServletRequest request,
                            RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession(false);
        if (session.getAttribute("user") != null){
            model.addAttribute("isStreamer",true);
            return "utilisateur/live-stream";
        }
        redirectAttributes.addFlashAttribute("error", "Vous devez vous connecter pour avoir accès à cette page");
        return "redirect:/authentification";
    }

    @GetMapping("/live/{pseudo}")
    public String joinLive(Model model,
                           HttpServletRequest request,
                           RedirectAttributes redirectAttributes,
                           @PathVariable String pseudo){
        HttpSession session = request.getSession(false);
        if (session.getAttribute("user") != null){
            model.addAttribute("isStreamer",true);
            model.addAttribute("pseudoStreamer", pseudo);
            return "utilisateur/live-stream";
        }
        redirectAttributes.addFlashAttribute("error", "Vous devez vous connecter pour avoir accès à cette page");
        return "redirect:/authentification";
    }

    @MessageMapping("/live/{pseudo}")
    @SendTo("/topic/live/{pseudo}")
    public String liveVideo(String offer){
        return offer;
    }

    @MessageMapping("/live/offers/{pseudo}")
    @SendTo("/topic/live/offers/{pseudo}")
    public String liveVideoOffers(String offer){
        return offer;
    }

    @MessageMapping("/live/answer/{pseudo}")
    @SendTo("/topic/live/answer/{pseudo}")
    public String liveVideoAnswers(String answer){
        return answer;
    }

    @MessageMapping("/live/candidate/{pseudo}")
    @SendTo("/topic/live/candidate/{pseudo}")
    public String liveVideoCandidate(String candidate){
        return candidate;
    }
}

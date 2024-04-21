package com.artcorp.artsync.controller.websocket;

import com.artcorp.artsync.entity.Utilisateur;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class LiveStreamController {
    private final SimpMessagingTemplate messagingTemplate;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public LiveStreamController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/live")
    public String live(){
        return "utilisateur/live-stream";
    }

    @GetMapping("/live/start")
    public String startLive(Model model,
                            HttpServletRequest request,
                            RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession(false);
        Utilisateur user = (Utilisateur) session.getAttribute("user");
        if (user != null){
            String pseudo = user.getPseudo();
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
        Utilisateur user = (Utilisateur) session.getAttribute("user");
        if (user != null){
            String viewerPseudo = user.getPseudo();
            model.addAttribute("isStreamer",false);
            model.addAttribute("pseudoStreamer", pseudo);
            return "utilisateur/live-stream";
        }
        redirectAttributes.addFlashAttribute("error", "Vous devez vous connecter pour avoir accès à cette page");
        return "redirect:/authentification";
    }

    @MessageMapping("/live/start/{pseudo}")
    @SendTo("/topic/live/start/{pseudo}")
    public String startLiveVideo(String start){
        return start;
    }

    @MessageMapping("/live/new/{pseudo}")
    @SendTo("/topic/live/new/{pseudo}")
    public String liveVideo(String pseudo){
        return pseudo;
    }

    @MessageMapping("/live/offer/{pseudo1}/{pseudo2}")
    @SendTo("/topic/live/offer/{pseudo1}/{pseudo2}")
    public String liveVideo(@DestinationVariable String pseudo1, String offer){
        LOGGER.info("offer : " + offer);
        return offer;
    }

    @MessageMapping("/live/answer/{pseudo1}/{pseudo2}")
    @SendTo("/topic/live/answer/{pseudo1}/{pseudo2}")
    public String liveVideoAnswers(@DestinationVariable String pseudo1, String answer){
        LOGGER.info("Received answer for user: " + pseudo1);
        LOGGER.info("Answer: " + answer);
        return answer;
    }

    @MessageMapping("/live/candidate/{pseudo1}/{pseudo2}")
    @SendTo("/topic/live/candidate/{pseudo1}/{pseudo2}")
    public String liveVideoCandidate(@DestinationVariable String pseudo1, String candidate){
        LOGGER.info("Received candidate for user: " + pseudo1);
        LOGGER.info("Candidate: " + candidate);
        return candidate;
    }

    @MessageMapping("/live/chat/{pseudo}")
    @SendTo("/topic/live/chat/{pseudo}")
    public String liveStreamChat(String chat){
        return chat;
    }
}

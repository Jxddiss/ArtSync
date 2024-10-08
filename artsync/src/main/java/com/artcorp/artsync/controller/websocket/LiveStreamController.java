package com.artcorp.artsync.controller.websocket;

import com.artcorp.artsync.entity.LiveStream;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.exception.domain.NotConnectedException;
import com.artcorp.artsync.service.impl.LiveStreamServiceImpl;
import com.artcorp.artsync.service.impl.UtilisateurServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.Set;

@Controller
public class LiveStreamController {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private UtilisateurServiceImpl utilisateurService;
    private LiveStreamServiceImpl liveStreamService;
    private Set<String> currentStreamer = new HashSet<>();

    @Autowired
    public LiveStreamController(UtilisateurServiceImpl utilisateurService, LiveStreamServiceImpl liveStreamService) {
        this.utilisateurService = utilisateurService;
        this.liveStreamService = liveStreamService;
    }

    @GetMapping("/live/start")
    public String startLive(Model model,
                            HttpServletRequest request,
                            RedirectAttributes redirectAttributes) throws NotConnectedException {
        HttpSession session = request.getSession(false);
        Utilisateur user = (Utilisateur) session.getAttribute("user");
        if (user != null){
            String streamerPic = user.getPhotoUrl();
            model.addAttribute("streamerPic",streamerPic);
            model.addAttribute("streamStarted",true);
            model.addAttribute("isStreamer",true);
            return "utilisateur/live-stream";
        }else {
            throw new NotConnectedException("Veuillez vous connecter");
        }
    }

    @GetMapping("/live/{pseudo}")
    public String joinLive(Model model,
                           HttpServletRequest request,
                           RedirectAttributes redirectAttributes,
                           @PathVariable String pseudo) throws NotConnectedException {
        HttpSession session = request.getSession(false);
        Utilisateur user = (Utilisateur) session.getAttribute("user");
        if (user != null){
            Utilisateur streamer = utilisateurService.findByPseudo(pseudo);
            if(streamer != null){
                String streamerPic = streamer.getPhotoUrl();
                LiveStream liveStream = liveStreamService.findByPseudo(pseudo);
                if (liveStream != null){
                    model.addAttribute("titre",liveStream.getTitre());
                }
                model.addAttribute("streamerPic",streamerPic);
                model.addAttribute("isStreamer",false);
                model.addAttribute("pseudoStreamer", pseudo);
                if (currentStreamer.contains(pseudo)){
                    model.addAttribute("streamStarted",true);
                }else{
                    model.addAttribute("streamStarted",false);
                }
                return "utilisateur/live-stream";
            }else{
                redirectAttributes.addFlashAttribute("error", "Stream inexistant");
                return "redirect:/index";
            }
        }else {
            throw new NotConnectedException("Veuillez vous connecter");
        }
    }

    @MessageMapping("/live/start/{pseudo}")
    @SendTo("/topic/live/start/{pseudo}")
    public String startLiveVideo(String titre, @DestinationVariable String pseudo){
        LOGGER.info("pseudo : "+pseudo);
        currentStreamer.add(pseudo);
        LiveStream liveStream = liveStreamService.findByPseudo(pseudo);
        if (liveStream == null){
            liveStream = new LiveStream();
        }
        liveStream.setActive(true);
        liveStream.setPseudoStreamer(pseudo);
        liveStream.setTitre(titre);
        liveStreamService.addLive(liveStream);
        return titre;
    }

    @MessageMapping("/live/new/{pseudo}")
    @SendTo("/topic/live/new/{pseudo}")
    public String liveVideo(String pseudo){
        return pseudo;
    }

    @MessageMapping("/live/offer/{pseudo1}/{pseudo2}")
    @SendTo("/topic/live/offer/{pseudo1}/{pseudo2}")
    public String liveVideoOffer(String offer){
        return offer;
    }

    @MessageMapping("/live/answer/{pseudo1}/{pseudo2}")
    @SendTo("/topic/live/answer/{pseudo1}/{pseudo2}")
    public String liveVideoAnswers(String answer){
        return answer;
    }

    @MessageMapping("/live/candidate/{pseudo1}/{pseudo2}")
    @SendTo("/topic/live/candidate/{pseudo1}/{pseudo2}")
    public String liveVideoCandidate(String candidate){
        return candidate;
    }

    @MessageMapping("/live/chat/{pseudo}")
    @SendTo("/topic/live/chat/{pseudo}")
    public String liveStreamChat(String chat){
        return chat;
    }

    @MessageMapping("/live/count/{pseudo}")
    @SendTo("/topic/live/count/{pseudo}")
    public String getViewersCount(String count){
        return count;
    }

    @MessageMapping("/live/leave/{pseudo}")
    @SendTo("/topic/live/leave/{pseudo}")
    public String leaveLive(String viewer){
        return viewer;
    }

    @MessageMapping("/live/end/{pseudo}")
    @SendTo("/topic/live/end/{pseudo}")
    public String endLive(String pseudoStreamer){
        currentStreamer.remove(pseudoStreamer);
        LiveStream liveStream = liveStreamService.findByPseudo(pseudoStreamer);
        liveStream.setActive(false);
        liveStreamService.addLive(liveStream);
        return pseudoStreamer;
    }
}

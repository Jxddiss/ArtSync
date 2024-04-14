package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.Conversation;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.service.ConversationService;
import com.artcorp.artsync.service.ProjetService;
import com.artcorp.artsync.service.impl.ConversationServiceImpl;
import com.artcorp.artsync.service.impl.ProjetServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/utilisateur/conversation")
public class ConversationController {
    private ConversationService conversationService;
    private ProjetService projetService;

    public ConversationController(ConversationServiceImpl conversationService, ProjetServiceImpl projetService) {
        this.conversationService = conversationService;
        this.projetService = projetService;
    }
    @GetMapping("")
    public String redirigerVersConversation(HttpServletRequest request,
                                            Model model,
                                            RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(false);
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");

        if (session != null && utilisateur != null) {
            List<Conversation> conversations = conversationService.findByAllByUtilisateur(utilisateur);

            if (conversations != null || !conversations.isEmpty()){
                model.addAttribute("conversations", conversations);
            }else{
                model.addAttribute("message", "Aucune conversation");
            }
            return "utilisateur/conversation";
        }
        redirectAttributes.addFlashAttribute("error", "Veuillez vous connecter");
        return "redirect:/authentification";
    }

    @GetMapping("/{id}")
    public String redirigerVersChat(@PathVariable("id") Long id, Model model) {
        Conversation conversation = conversationService.findById(id);
        model.addAttribute("conversation", conversation);
        return "utilisateur/chat-prive";
    }
}

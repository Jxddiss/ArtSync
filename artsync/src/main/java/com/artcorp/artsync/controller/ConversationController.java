package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.*;
import com.artcorp.artsync.exception.domain.NotConnectedException;
import com.artcorp.artsync.service.ConversationService;
import com.artcorp.artsync.service.PostService;
import com.artcorp.artsync.service.ProjetService;
import com.artcorp.artsync.service.UtilisateurService;
import com.artcorp.artsync.service.impl.ConversationServiceImpl;
import com.artcorp.artsync.service.impl.PostServiceImpl;
import com.artcorp.artsync.service.impl.ProjetServiceImpl;
import com.artcorp.artsync.service.impl.UtilisateurServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/utilisateur/conversation")
public class ConversationController {
    private ConversationService conversationService;
    private PostService postService;
    private ProjetService projetService;
    private UtilisateurService utilisateurService;

    public ConversationController(ConversationServiceImpl conversationService,
                                  ProjetServiceImpl projetService,
                                  PostServiceImpl postService,
                                  UtilisateurServiceImpl utilisateurService) {
        this.conversationService = conversationService;
        this.projetService = projetService;
        this.postService = postService;
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("")
    public String redirigerVersConversation(HttpServletRequest request,
                                            Model model,
                                            @AuthenticationPrincipal String username,
                                            RedirectAttributes redirectAttributes) throws NotConnectedException {
        HttpSession session = request.getSession(false);
        List<Conversation> conversations;
        if (session != null) {
            Utilisateur utilisateur = utilisateurService.addUserSessionIfNot(session,username);
            List<Projet> projets = projetService.findProjectsOfUser(utilisateur.getId());
            conversations = GetConversationsAndAddToModel(model, session, utilisateur,projets);
            if(!conversations.isEmpty()){
                return "redirect:/utilisateur/conversation/" + conversations.get(0).getId();
            }else{
                redirectAttributes.addFlashAttribute("warn", "Vous n'avez aucunes conversations");
                return "redirect:/feed";
            }
        }
        redirectAttributes.addFlashAttribute("error", "Veuillez vous connecter");
        return "redirect:/authentification";
    }

    @GetMapping("/{id}")
    public String redirigerVersChat(@PathVariable("id") Long id,
                                    @RequestParam(name = "appel",required = false) boolean appel,
                                    @AuthenticationPrincipal String username,
                                    HttpServletRequest request,
                                    Model model,
                                    RedirectAttributes redirectAttributes) throws NotConnectedException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            Utilisateur utilisateur = utilisateurService.addUserSessionIfNot(session,username);
            List<Projet> projets = projetService.findProjectsOfUser(utilisateur.getId());
            checkConversationInSession(model, session, utilisateur, projets);

            Conversation conversation = conversationService.findById(id);

            if(conversation != null){
                if(Objects.equals(conversation.getUtilisateurUn().getId(), utilisateur.getId())
                        || (conversation.getUtilisateurDeux() != null && Objects.equals(conversation.getUtilisateurDeux().getId(), utilisateur.getId()))
                        || (conversation.getProjet() != null && projets.contains(conversation.getProjet())))
                {

                    Utilisateur amiCourrant = Objects.equals(conversation.getUtilisateurUn().getId(), utilisateur.getId())
                            ? conversation.getUtilisateurDeux() : conversation.getUtilisateurUn();

                    Post banniere = postService.findBanniereUtilisateur(amiCourrant);
                    model.addAttribute("banniere", banniere);
                    model.addAttribute("amiCourrant", amiCourrant);
                    model.addAttribute("conversationCourrante", conversation);
                    if (conversation.getProjet()!=null){
                        return "redirect:projet/"+conversation.getId();
                    }
                    if(appel){
                        model.addAttribute("appel",true);
                    }
                    return "utilisateur/chat-prive";
                }
            }
            redirectAttributes.addFlashAttribute("warn", "Conversation inexistante");
            return "redirect:/feed";
        }
        redirectAttributes.addFlashAttribute("error", "Veuillez vous connecter");
        return "redirect:/authentification";
    }
    @GetMapping("/projet/{id}")
    public String redirigerVersChatGroupe(@PathVariable("id") Long id,
                                    HttpServletRequest request,
                                    Model model,
                                    @AuthenticationPrincipal String username,
                                    RedirectAttributes redirectAttributes) throws NotConnectedException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            Utilisateur utilisateur = utilisateurService.addUserSessionIfNot(session,username);
            List<Conversation> conversations = conversationService.findByAllByUtilisateur(utilisateur);

            List<Projet> projets = projetService.findProjectsOfUser(utilisateur.getId());
            checkConversationInSession(model, session, utilisateur,projets);

            Conversation conversation = conversationService.findById(id);
            model.addAttribute("conversationCourrante", conversation);
            return "utilisateur/chat-groupe";
        }

        redirectAttributes.addFlashAttribute("error", "Veuillez vous connecter");
        return "redirect:/authentification";
    }

    private void checkConversationInSession(Model model, HttpSession session, Utilisateur utilisateur, List<Projet> projets) {
        if(session.getAttribute("conversationsAmi") == null){
            GetConversationsAndAddToModel(model, session, utilisateur, projets);
        }else{
            model.addAttribute("conversationsAmi", session.getAttribute("conversationsAmi"));
            model.addAttribute("conversationsProjet",session.getAttribute("conversationsProjet"));
        }
    }

    private List<Conversation> GetConversationsAndAddToModel(Model model, HttpSession session, Utilisateur utilisateur, List<Projet> projets) {
        List<Conversation> conversations = conversationService.findByAllByUtilisateur(utilisateur);
        ArrayList<Conversation> conversationAmi = new ArrayList<>();
        ArrayList<Conversation> conversationProjet = new ArrayList<>();



        for (Projet projet: projets){
            if (conversationService.findByProjet(projet) != null){
                conversationProjet.add(conversationService.findByProjet(projet));
            }
        }
        for (Conversation conversation:conversations){
            if (conversation.getProjet()==null){
                conversationAmi.add(conversation);
            }
        }
        model.addAttribute("conversationsAmi", conversationAmi);
        model.addAttribute("conversationsProjet",conversationProjet);
        session.setAttribute("conversationsAmi", conversationAmi);
        session.setAttribute("conversationsProjet",conversationProjet);

        return conversations;
    }
}

package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.*;
import com.artcorp.artsync.exception.domain.FileFormatException;
import com.artcorp.artsync.exception.domain.NotConnectedException;
import com.artcorp.artsync.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;

import static com.artcorp.artsync.constant.FileConstant.USER_FOLDER;

@Controller
public class GroupController {
    @Autowired
    UtilisateurService userService;
    @Autowired
    ProjetService projetService;
    @Autowired
    DemandeService demandeService;
    @Autowired
    AnnonceService annonceService;
    @Autowired
    TacheService tacheService;
    @Autowired
    FichierService fichierService;
    @Autowired
    ConversationService conversationService;
    @Autowired
    ChatService chatService;
    @GetMapping("/group/join")
    public String rejoindreGroup(@RequestParam("id") Long id,
                                 @RequestParam("type") String type,
                                 @AuthenticationPrincipal String username,
                                 HttpServletRequest request) throws NotConnectedException {

        HttpSession session = request.getSession(false);
        Long idUtilisateur = null;

        if (session != null){
            Utilisateur utilisateur = userService.addUserSessionIfNot(session,username);
            idUtilisateur = utilisateur.getId();
            if ("rejoindre".equals(type)) {
                if (!projetService.findById(id).isPublique()) {
                    demandeService.createDemande(idUtilisateur, id);
                    return "recherche";
                }
                projetService.addUtilisateurToProjet(id, idUtilisateur);
            } else if ("quitter".equals(type)) {
                projetService.removeUtilisateurFromProjet(id, idUtilisateur);
            }
            return "recherche";
        }else{
            throw new NotConnectedException("Veuillez vous connecter");
        }
    }

    @GetMapping("/groupe/group/{projetId}")
    public String redirigerVersProjet(@PathVariable("projetId") Long projectId,
                                      @AuthenticationPrincipal String username,
                                      Model model,
                                      HttpServletRequest request) throws NotConnectedException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Utilisateur utilisateur = userService.addUserSessionIfNot(session,username);
            model.addAttribute("selected", "dashboard");
            model.addAttribute("utilisateur", utilisateur);
            model.addAttribute("nbFichiers", fichierService.countByProjet(projetService.findById(projectId)));
            model.addAttribute("projet", projetService.findById(projectId));
            model.addAttribute("conversation", conversationService.findByProjet(projetService.findById(projectId)));
            model.addAttribute("projets", projetService.findProjectsOfUser(utilisateur.getId()));
            model.addAttribute("nbMembres", projetService.getMembersCount(projectId));
            model.addAttribute("annonces", annonceService.findByProjetId(projectId));
            model.addAttribute("taches", tacheService.findByEtatAndProjetId("En cours", projectId));
            model.addAttribute("tachesAFaire", tacheService.findByEtatAndProjetId("À faire", projectId));
            model.addAttribute("tachesTerminees", tacheService.findByEtatAndProjetId("Terminé", projectId));
            return "groupe/group";
        }else {
            throw new NotConnectedException("Veuillez vous connecter");
        }
    }

    @GetMapping("/groupe/group-users/{projetId}")
    public String getMembresProjet(@PathVariable("projetId") Long projectId,
                                   Model model,
                                   @AuthenticationPrincipal String username,
                                   HttpServletRequest request) throws NotConnectedException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Utilisateur utilisateur = userService.addUserSessionIfNot(session,username);
            model.addAttribute("selected", "users");
            model.addAttribute("utilisateur", utilisateur);
            model.addAttribute("projet", projetService.findById(projectId));
            model.addAttribute("conversation", conversationService.findByProjet(projetService.findById(projectId)));
            model.addAttribute("projets", projetService.findProjectsOfUser(utilisateur.getId()));
            model.addAttribute("membres", projetService.getMembers(projectId));
            model.addAttribute("nbMembres", projetService.getMembersCount(projectId));
            model.addAttribute("nbFichiers", fichierService.countByProjet(projetService.findById(projectId)));
            model.addAttribute("annonces", annonceService.findByProjetId(projectId));
            return "groupe/group-users";
        }else{
            throw new NotConnectedException("Veuillez vous connecter");
        }
    }
    @PostMapping("/groupe/group-users/recherche")
    public String getMembresProjet(@RequestParam("projetId") Long projectId,
                                   @RequestParam("keyword") String keyword,
                                   Model model,
                                   HttpServletRequest request,
                                   @AuthenticationPrincipal String username) throws NotConnectedException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
            model.addAttribute("selected", "users");
            model.addAttribute("utilisateur", utilisateur);
            model.addAttribute("projet", projetService.findById(projectId));
            model.addAttribute("conversation", conversationService.findByProjet(projetService.findById(projectId)));
            model.addAttribute("projets", projetService.findProjectsOfUser(utilisateur.getId()));
            model.addAttribute("membres", projetService.findByKeyWordAndProjet(projectId, keyword));
            model.addAttribute("nbMembres", projetService.getMembersCount(projectId));
            model.addAttribute("nbFichiers", fichierService.countByProjet(projetService.findById(projectId)));
            model.addAttribute("annonces", annonceService.findByProjetId(projectId));
            return "groupe/group-users";
        }else{
            throw new NotConnectedException("Veuillez vous connecter");
        }
    }
    @GetMapping("/groupe/group-tache/{projetId}")
    public String getTacheProjet(@PathVariable("projetId") Long projectId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        model.addAttribute("selected", "taches");
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("projet", projetService.findById(projectId));
        model.addAttribute("conversation",conversationService.findByProjet(projetService.findById(projectId)));
        model.addAttribute("projets", projetService.findProjectsOfUser(utilisateur.getId()));
        model.addAttribute("nbMembres", projetService.getMembersCount(projectId));
        model.addAttribute("nbFichiers", fichierService.countByProjet(projetService.findById(projectId)));
        model.addAttribute("annonces", annonceService.findByProjetId(projectId));
        model.addAttribute("taches", tacheService.findByProjetId(projectId));

        return "groupe/group-tache";
    }

    @PostMapping("/groupe/group-tache/recherche")
    public String rechercheTache(@RequestParam("projetId") Long projectId,
                                 @RequestParam("keyword") String keyword,
                                 @AuthenticationPrincipal String username,
                                 Model model, HttpServletRequest request) throws NotConnectedException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Utilisateur utilisateur = userService.addUserSessionIfNot(session,username);
            model.addAttribute("selected", "taches");
            model.addAttribute("utilisateur", utilisateur);
            model.addAttribute("projet", projetService.findById(projectId));
            model.addAttribute("conversation",conversationService.findByProjet(projetService.findById(projectId)));
            model.addAttribute("projets", projetService.findProjectsOfUser(utilisateur.getId()));
            model.addAttribute("nbMembres", projetService.getMembersCount(projectId));
            model.addAttribute("nbFichiers", fichierService.countByProjet(projetService.findById(projectId)));
            model.addAttribute("annonces", annonceService.findByProjetId(projectId));
            model.addAttribute("taches", tacheService.findByKeyword(keyword,projectId));

            return "groupe/group-tache";
        }else{
            throw new NotConnectedException("Veuillez vous connecter");
        }
    }
    @GetMapping("/groupe/group-demande/{projetId}")
    public String getDemandeProjet(@PathVariable("projetId") Long projectId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        model.addAttribute("selected", "demandes");
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("projet", projetService.findById(projectId));
        model.addAttribute("conversation",conversationService.findByProjet(projetService.findById(projectId)));
        model.addAttribute("projets", projetService.findProjectsOfUser(utilisateur.getId()));
        model.addAttribute("nbMembres", projetService.getMembersCount(projectId));
        model.addAttribute("nbFichiers", fichierService.countByProjet(projetService.findById(projectId)));
        model.addAttribute("annonces", annonceService.findByProjetId(projectId));
        model.addAttribute("demandes", demandeService.findByProjetId(projectId));

        return "groupe/group-demande";
    }
    @GetMapping("/groupe/group-gestion/{projetId}")
    public String redirectGestion(@PathVariable("projetId") Long projectId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        if (!projetService.findById(projectId).getAdmin().getId().equals(utilisateur.getId())) {
            return "groupe/group";
        }
        model.addAttribute("selected", "gestion");
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("projet", projetService.findById(projectId));
        model.addAttribute("conversation",conversationService.findByProjet(projetService.findById(projectId)));
        model.addAttribute("projets", projetService.findProjectsOfUser(utilisateur.getId()));
        model.addAttribute("nbMembres", projetService.getMembersCount(projectId));
        model.addAttribute("nbFichiers", fichierService.countByProjet(projetService.findById(projectId)));
        model.addAttribute("annonces", annonceService.findByProjetId(projectId));

        return "groupe/group-gestion";
    }
    @PostMapping("groupe/group/{projetId}")
    public String handleAnnonceSubmission(@PathVariable Long projetId, @RequestParam("message") String message) {
        Annonce annonce = new Annonce();
        annonce.setProjet(projetService.findById(projetId));
        annonce.setUtilisateur(annonce.getProjet().getAdmin());
        annonce.setDateCreation(java.time.LocalDateTime.now());
        annonce.setMessage(message);
        annonceService.createAnnonce(annonce);
        return "redirect:/groupe/group/{projetId}";
    }

    @GetMapping("/groupe/create/{projetId}")
    public String createTache(@PathVariable("projetId") Long projetId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        model.addAttribute("selected", "taches");
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("membres", projetService.getMembers(projetId));
        model.addAttribute("projet", projetService.findById(projetId));
        model.addAttribute("conversation",conversationService.findByProjet(projetService.findById(projetId)));
        model.addAttribute("projets", projetService.findProjectsOfUser(utilisateur.getId()));
        model.addAttribute("nbMembres", projetService.getMembersCount(projetId));
        model.addAttribute("nbFichiers", fichierService.countByProjet(projetService.findById(projetId)));
        model.addAttribute("annonces", annonceService.findByProjetId(projetId));
        return "groupe/group-createTache";
    }

    @PostMapping("groupe/create/tache/{projetId}")
    public String createTache(@PathVariable Long projetId,
                              @RequestParam("titre") String titre,
                              @RequestParam("description") String description,
                              @RequestParam("etat") String etat,
                              @RequestParam("utilisateur") Long utilisateurId,
                              @RequestParam(value = "tacheId", defaultValue = "-1") Long tacheId,
                              Model model,
                              HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        Utilisateur user = userService.findById(utilisateurId);
        Tache tache = (tacheId != -1) ? tacheService.findById(tacheId) : new Tache();
        tache.setTitre(titre);
        tache.setDescription(description);
        tache.setEtat(etat);
        tache.setProjet(projetService.findById(projetId));
        tache.setUtilisateur(user);
        tache.setDateCreation(LocalDateTime.now());
        tache.setDateModification(LocalDateTime.now());

        if (tacheId != -1) {
            tacheService.updateTache(tache);
        } else {
            tacheService.createTache(tache);
        }
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("selected", "taches");
        model.addAttribute("projet", projetService.findById(projetId));
        model.addAttribute("conversation",conversationService.findByProjet(projetService.findById(projetId)));
        model.addAttribute("projets", projetService.findProjectsOfUser(utilisateur.getId()));
        model.addAttribute("nbMembres", projetService.getMembersCount(projetId));
        model.addAttribute("nbFichiers", fichierService.countByProjet(projetService.findById(projetId)));
        model.addAttribute("annonces", annonceService.findByProjetId(projetId));

        return "redirect:/groupe/group-tache/{projetId}";
    }
    @GetMapping("groupe/group-tache/{projetId}/{tacheId}")
    public String deleteTask(@PathVariable Long projetId,
                             @PathVariable Long tacheId,
                             Model model,
                             HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        model.addAttribute("selected", "taches");
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("projet", projetService.findById(projetId));
        model.addAttribute("conversation",conversationService.findByProjet(projetService.findById(projetId)));
        model.addAttribute("projets", projetService.findProjectsOfUser(utilisateur.getId()));
        model.addAttribute("nbMembres", projetService.getMembersCount(projetId));
        model.addAttribute("nbFichiers", fichierService.countByProjet(projetService.findById(projetId)));
        model.addAttribute("annonces", annonceService.findByProjetId(projetId));
        tacheService.deleteTache(tacheId);

        return "redirect:/groupe/group-tache/{projetId}";
    }

    @GetMapping("groupe/group-tache/update/{projetId}/{tacheId}")
    public String updateTask(@PathVariable Long projetId,
                             @PathVariable Long tacheId,
                             Model model,
                             HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        model.addAttribute("selected", "taches");
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("projet", projetService.findById(projetId));
        model.addAttribute("conversation",conversationService.findByProjet(projetService.findById(projetId)));
        model.addAttribute("projets", projetService.findProjectsOfUser(utilisateur.getId()));
        model.addAttribute("membres", projetService.getMembers(projetId));
        model.addAttribute("nbMembres", projetService.getMembersCount(projetId));
        model.addAttribute("nbFichiers", fichierService.countByProjet(projetService.findById(projetId)));
        model.addAttribute("annonces", annonceService.findByProjetId(projetId));
        Tache tache = tacheService.findById(tacheId);
        model.addAttribute("tache", tache);

        return "groupe/group-createTache";
    }
    @GetMapping("groupe/group-demande/refuser/{projetId}/{demandeId}")
    public String refuserDemande(@PathVariable Long projetId,
                                 @PathVariable Long demandeId,
                                 Model model,
                                 HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        demandeService.deleteDemande(demandeId);

        return "redirect:/groupe/group-demande/{projetId}";
    }
    @GetMapping("groupe/group-demande/accepter/{projetId}/{demandeId}")
    public String accepterDemande(@PathVariable Long projetId,
                                  @PathVariable Long demandeId,
                                  Model model,
                                  HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Optional<Demande> demande = demandeService.findById(demandeId);
        Utilisateur userToAdd = demande.get().getUtilisateur();
        projetService.addUtilisateurToProjet(projetId, userToAdd.getId());
        demandeService.deleteDemande(demandeId);
        projetService.addUtilisateurToProjet(projetId, userToAdd.getId());
        return "redirect:/groupe/group-demande/{projetId}";
    }
    @PostMapping("/groupe/update/{projetId}")
    public String updateProjet(@PathVariable("projetId") Long projetId,
                               @RequestParam("titre") String titre,
                               @RequestParam("description") String description,
                               @RequestParam(value = "private", defaultValue = "false") boolean isPrivate,
                               @RequestParam(name = "upload", required = false) MultipartFile image,
                               Model model,
                               HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Projet projet = projetService.findById(projetId);
        projet.setTitre(titre);
        projet.setDescription(description);
        projet.setPublique(isPrivate);

        String originalFilename = StringUtils.cleanPath(image.getOriginalFilename());

        if (!originalFilename.isEmpty()){

            File parentDir = new File(USER_FOLDER);
            File saveFile = new File(parentDir.getAbsolutePath() + File.separator + originalFilename);
            Files.copy(image.getInputStream(),saveFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            image.getInputStream().close();
            projet.setProjetPhoto(originalFilename);
        }

        projetService.updateProjet(projet);

        return "redirect:/groupe/group/"+projet.getId();
    }

    @PostMapping("/groupe/create")
    public String createProjet(@RequestParam("titre") String titre,
                               @RequestParam("description") String description,
                               @RequestParam(value = "private", defaultValue = "false") boolean isPrivate,
                               @RequestParam(name = "upload", required = false) MultipartFile image,
                               Model model,
                               HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        model.addAttribute("selected", "dashboard");
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("projets", projetService.findProjectsOfUser(utilisateur.getId()));
        model.addAttribute("nbMembres", projetService.getMembersCount(utilisateur.getId()));
        model.addAttribute("nbFichiers", projetService.getFileCount(utilisateur.getId()));
        model.addAttribute("annonces", annonceService.findByProjetId(utilisateur.getId()));

        Projet projet = new Projet();
        projet.setTitre(titre);
        projet.setDescription(description);
        projet.setDateCreation(LocalDateTime.now());
        projet.setDateModification(LocalDateTime.now());
        projet.setPublique(isPrivate);
        projet.setAdmin(utilisateur);
        projet.setUtilisateurs(List.of(utilisateur));

        String originalFilename = StringUtils.cleanPath(image.getOriginalFilename());
        projet.setProjetPhoto(originalFilename);

        if (!originalFilename.isEmpty()){
            File parentDir = new File(USER_FOLDER);
            File saveFile = new File(parentDir.getAbsolutePath() + File.separator + originalFilename);
            Files.copy(image.getInputStream(),saveFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            image.getInputStream().close();
        }else{
            projet.setProjetPhoto("aurora.jpg");
        }

        projetService.createProjet(projet);

        Conversation conversation = new Conversation();
        conversation.setUtilisateurUn(projet.getAdmin());
        conversation.setProjet(projet);
        conversationService.createConvo(conversation);


        return "redirect:/groupe/group/" + projet.getId();
    }

    @GetMapping("groupe/group-folder/{projetId}")
    public String redirigerFichier(@PathVariable("projetId") Long projetId,
                                   Model model,
                                   HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        model.addAttribute("selected", "fichiers");
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("projet", projetService.findById(projetId));
        model.addAttribute("conversation",conversationService.findByProjet(projetService.findById(projetId)));
        model.addAttribute("projets", projetService.findProjectsOfUser(utilisateur.getId()));
        model.addAttribute("membres", projetService.getMembers(projetId));
        model.addAttribute("nbMembres", projetService.getMembersCount(projetId));
        model.addAttribute("nbFichiers", fichierService.countByProjet(projetService.findById(projetId)));
        model.addAttribute("annonces", annonceService.findByProjetId(projetId));
        return "groupe/group-folders";
    }
    @GetMapping("groupe/group-fichier/{projetId}/{userId}")
    public String redirigerFichier(@PathVariable("projetId") Long projetId,
                                   @PathVariable("userId") Long userId,
                                   Model model,
                                   HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Utilisateur user = userService.findById(userId);
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("selected", "fichiers");
        model.addAttribute("projet", projetService.findById(projetId));
        model.addAttribute("conversation",conversationService.findByProjet(projetService.findById(projetId)));
        model.addAttribute("projets", projetService.findProjectsOfUser(utilisateur.getId()));
        model.addAttribute("user", user);
        model.addAttribute("fichiers", fichierService.findAllByProjetAndUtilisateur(projetService.findById(projetId), userService.findById(userId)));
        model.addAttribute("nbMembres", projetService.getMembersCount(projetId));
        model.addAttribute("nbFichiers", fichierService.countByProjet(projetService.findById(projetId)));
        model.addAttribute("annonces", annonceService.findByProjetId(projetId));
        return "groupe/group-fichier";
    }
    @PostMapping("/groupe/addFile/{projetId}")
    public String addFile(@PathVariable Long projetId,
                          @RequestParam("userID") Long userId,
                          @RequestParam("fileUpload") MultipartFile image,
                          RedirectAttributes redirectAttributes) throws FileFormatException, IOException {
        FichierGeneral fichier = new FichierGeneral();
        fichier.setUrlMedia(image.getOriginalFilename());
        fichier.setProjet(projetService.findById(projetId));
        fichier.setUtilisateur(userService.findById(userId));
        fichierService.createFichierProjet(fichier, image);
        redirectAttributes.addFlashAttribute("succes","Fichier ajoutées avec succes");
        return "redirect:/groupe/group-fichier/" + projetId + "/" + userId;
    }

    @GetMapping("/groupe/group/quitter/{projetId}")
    public String quitterProjet(@PathVariable("projetId") Long projetId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
        projetService.removeUtilisateurFromProjet(projetId, utilisateur.getId());
        return "redirect:/utilisateur/relation/groupes";
    }
    @GetMapping("/groupe/group/supprimer/{projetId}")
    public String supprimerProjet(@PathVariable("projetId") Long projetId, HttpServletRequest request, HttpMethod method) throws NoResourceFoundException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "auth";
        }
        Projet projet = projetService.findById(projetId);
        if (projet == null){
            throw new NoResourceFoundException(method,"Projet id: "+projetId);
        }
        List<Utilisateur> users = projetService.getMembers(projetId);
        for (Utilisateur user : users) {
            projetService.removeUtilisateurFromProjet(projetId, user.getId());
        }
        fichierService.deleteAllByProjet(projet);
        annonceService.deleteAllByProjetId(projetId);
        tacheService.deleteAllByProjetId(projetId);
        demandeService.deleteAllByProjetId(projetId);
        chatService.deleteAllByConversationId(conversationService.findByProjet(projetService.findById(projetId)).getId());
        conversationService.deleteAllByProjetId(projetId);
        projetService.deleteProjet(projetId);
        return "redirect:/utilisateur/relation/groupes";
    }
    @GetMapping("/groupe/group/supprimer/user/{projetId}/{userId}")
    public String supprimerUtilisateur(@PathVariable("projetId") Long projetId, @PathVariable("userId") Long userId) {
        projetService.removeUtilisateurFromProjet(projetId, userId);
        return "redirect:/groupe/group-users/" + projetId;
    }

}

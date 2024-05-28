package com.artcorp.artsync.service.impl;

import com.artcorp.artsync.entity.*;
import com.artcorp.artsync.exception.domain.MauvaisIdentifiantException;
import com.artcorp.artsync.exception.domain.NotConnectedException;
import com.artcorp.artsync.repos.*;
import com.artcorp.artsync.service.UtilisateurService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.artcorp.artsync.constant.EmailConstant.BASE_SITE_ADDRESS;

@Service
@Transactional
public class UtilisateurServiceImpl implements UtilisateurService, UserDetailsService {
    private UtilisateurRepos repos;
    private ConversationRepos conversationRepos;
    private ConfirmationTokenRepos confirmationTokenRepos;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final TacheRepos tacheRepos;
    private final AnnonceRepos annonceRepos;
    private final FichierServiceImpl fichierService;
    private final DemandeRepos demandeRepos;
    private final ForumRepos forumRepos;
    private final CommentaireRepos commentaireRepos;
    private final ChatRepos chatRepos;
    private final PostServiceImpl postService;
    private final ProjetServiceImpl projetService;
    private final PortfolioRepos portfolioRepos;

    public UtilisateurServiceImpl(UtilisateurRepos repos,
                                  ConversationRepos conversationRepos,
                                  ConfirmationTokenRepos confirmationTokenRepos,
                                  BCryptPasswordEncoder bCryptPasswordEncoder,
                                  TacheRepos tacheRepos,
                                  AnnonceRepos annonceRepos,
                                  FichierServiceImpl fichierService,
                                  DemandeRepos demandeRepos,
                                  ForumRepos forumRepos,
                                  CommentaireRepos commentaireRepos,
                                  ChatRepos chatRepos,
                                  PostServiceImpl postService,
                                  ProjetServiceImpl projetService,
                                  PortfolioRepos portfolioRepos) {
        this.repos = repos;
        this.conversationRepos = conversationRepos;
        this.confirmationTokenRepos = confirmationTokenRepos;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tacheRepos = tacheRepos;
        this.annonceRepos = annonceRepos;
        this.fichierService = fichierService;
        this.demandeRepos = demandeRepos;
        this.forumRepos = forumRepos;
        this.commentaireRepos = commentaireRepos;
        this.chatRepos = chatRepos;
        this.postService = postService;
        this.projetService = projetService;
        this.portfolioRepos = portfolioRepos;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur user = repos.findByPseudo(username);
        if (user == null){
            LOGGER.error("Utilisateur non trouvé avec le pseudo : "+username);
            throw new UsernameNotFoundException("Utilisateur non trouvé avec le pseudo : "+username);
        }else{
            UserPrincipal userPrincipal = new UserPrincipal(user);
            LOGGER.info("FOUND USER BY USERNAME" + username);
            return userPrincipal;
        }
    }

    @Override
    public Utilisateur connexion(String username, String password) throws MauvaisIdentifiantException {

        Utilisateur user = repos.findByPseudo(username);
        if(user != null){
            if(bCryptPasswordEncoder.matches(password, user.getPassword())){
                return user;
            }else{
                throw new MauvaisIdentifiantException("Mauvais identifiants");
            }
        }else{
            throw new MauvaisIdentifiantException("Mauvais identifiants");
        }
    }

    @Override
    public Utilisateur inscription(String pseudo, String prenom, String nom, String email, String password, String photoUrl, String specialisation, String statut) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setPseudo(pseudo);
        utilisateur.setPrenom(prenom);
        utilisateur.setNom(nom);
        utilisateur.setEmail(email);
        utilisateur.setPassword(encodePassword(password));
        utilisateur.setPhotoUrl(photoUrl);
        utilisateur.setSpecialisation(specialisation);
        utilisateur.setStatut(statut);
        utilisateur.setActive(true);
        utilisateur.setRole("ROLE_USER");
        utilisateur.setFollowers(new HashSet<>());
        utilisateur.setAmis(new HashSet<>());
        utilisateur.setFollowing(new HashSet<>());
        return repos.save(utilisateur);
    }

    @Override
    public List<Utilisateur> findAll() {
        return repos.findAll();
    }

    @Override
    public List<Utilisateur> findAllAdmin() {
        return repos.findAllAdmin();
    }


    @Override
    public Utilisateur findByPseudo(String pseudo) {
        return repos.findByPseudo(pseudo);
    }

    @Override
    public Utilisateur findByEmail(String email) {
        return repos.findByEmail(email);
    }

    @Override
    public Utilisateur findById(Long idUtilisateur) {
        Utilisateur utilisateur = null;
        if (repos.findById(idUtilisateur).isPresent()){
            utilisateur = repos.findById(idUtilisateur).get();
        }
        return utilisateur;
    }

    @Override
    public String genLinkPasswordReset(String email) {
        Utilisateur user = findByEmail(email);
        if (user != null){
            String token = String.valueOf(UUID.randomUUID());
            ConfirmationToken confirmationToken = new ConfirmationToken();
            confirmationToken.setUserId(user.getId());
            confirmationToken.setToken(token);
            confirmationToken.setType("PASSWORD_RESET");
            confirmationToken.setDateExpiration(java.sql.Date.valueOf(java.time.LocalDate.now().plusDays(1)));
            confirmationTokenRepos.save(confirmationToken);
            return BASE_SITE_ADDRESS+token;
        }
        return "";
    }

    @Override
    public boolean changePasswordFromToken(String password, Long userId,String token) {
        int i = repos.changePassword(encodePassword(password),userId);
        confirmationTokenRepos.delete(confirmationTokenRepos.findByToken(token,new Date()));
        return i == 1;
    }

    @Override
    public boolean emailIsValid(String email, Long userId) {
        return !repos.existsByEmailAndIdNot(email, userId);
    }

    @Override
    public boolean pseudoIsValid(String pseudo, Long userId) {
        return !repos.existsByPseudoAndIdNot(pseudo,userId);
    }

    @Override
    public Utilisateur addUserSessionIfNot(HttpSession session,String username) throws NotConnectedException {

        if(username.equalsIgnoreCase("anonymousUser")){
            throw new NotConnectedException("Veuiller vous connecter");
        }

        Utilisateur user = (Utilisateur) session.getAttribute("user");
        System.out.println(username);
        if (repos.existsByPseudoAndIdNot(username,0L)){
            if(user == null){
                user = repos.findByPseudo(username);
                if (user == null){
                    throw new NotConnectedException("Veuiller vous connecter");
                }
                session.setAttribute("user",user);
            }
        }else {
            SecurityContextHolder.clearContext();
            session.invalidate();
        }
        return user;
    }

    @Override
    public Utilisateur update(Utilisateur utilisateur) {
        return repos.save(utilisateur);
    }

    @Override
    public Notification updateRelations(Long followedId, Long followerId) {
        Utilisateur followed = repos.findById(followedId).get();
        Utilisateur follower = repos.findById(followerId).get();
        Notification notif = null;

        if (followed != null && follower != null) {
            if (followed.getFollowers().contains(follower)) {
                followed.getFollowers().remove(follower);
                follower.getFollowing().remove(followed);
                if (followed.getAmis().contains(follower)) {
                    followed.getAmis().remove(follower);
                    follower.getAmis().remove(followed);
                    Conversation conversation = conversationRepos.findByUtilisateurDeuxAndUtilisateurUn(followed, follower);
                    if (conversation != null){
                        conversationRepos.delete(conversation);
                    }
                }
            } else {
                followed.getFollowers().add(follower);
                follower.getFollowing().add(followed);
                notif = prepareNotif(followed,follower);

                if (followed.getFollowing().contains(follower)) {
                    followed.getAmis().add(follower);
                    follower.getAmis().add(followed);
                    Conversation conversation = conversationRepos.findByUtilisateurDeuxAndUtilisateurUn(followed, follower);
                    if (conversation == null) {
                        conversation = new Conversation();
                        conversation.setUtilisateurUn(followed);
                        conversation.setUtilisateurDeux(follower);
                        conversationRepos.save(conversation);
                    }
                }
            }
            repos.save(followed);
            repos.save(follower);
        }
        return notif;
    }

    @Override
    public List<Utilisateur> findBySpecialisation(String specialisation) {
        return repos.findBySpecialisationAndIsActive(specialisation);
    }

    @Override
    public List<Utilisateur> findByKeyword(String keyword) {
        return repos.findByKeyword(keyword);
    }

    @Override
    public void disable(Long id) {
        Utilisateur utilisateur = repos.findById(id).get();
        if (utilisateur != null) {
            utilisateur.setActive(false);
            repos.save(utilisateur);
        }
    }

    @Override
    public void enable(Long id) {
        Utilisateur utilisateur = repos.findById(id).get();
        if (utilisateur != null) {
            utilisateur.setActive(true);
            repos.save(utilisateur);
        }
    }

    @Override
    public void delete(Long userId) {
        if (repos.findById(userId).isPresent()){
            Utilisateur user = repos.findById(userId).get();
            List<Post> posts = postService.findPostByUser(user);
            for (Post post: posts){
                post.getListeFichiers().forEach(fichierGeneral -> {
                    fichierService.deleteById(fichierGeneral.getId(),"post");
                });
                postService.deletePost(post);
            }
            System.out.println("POSTS SUPPRIMÉS");
            List<Conversation> conversations = conversationRepos.findByAllByUtilisateur(user);
            for (Conversation conversation: conversations){
                if (conversation.getProjet()==null){
                    chatRepos.deleteAllByConversationId(conversation.getId());
                    conversationRepos.deleteById(conversation.getId());
                }
            }
            chatRepos.deleteAllByUtilisateurUnId(user.getId());
            System.out.println("CONVERSATION ET CHATS SUPPRIMÉES");
            List<Projet> projets = projetService.findProjectsOfUser(user.getId());
            for (Projet projet:projets){
                if (projet.getAdmin().getId().equals(user.getId())){
                    List<Utilisateur> users = projetService.getMembers(projet.getId());
                    for (Utilisateur utilisateur : users) {
                        projetService.removeUtilisateurFromProjet(projet.getId(), user.getId());
                    }
                    fichierService.deleteAllByProjet(projetService.findById(projet.getId()));
                    annonceRepos.deleteAllByProjetId(projet.getId());
                    tacheRepos.deleteAllByProjetId(projet.getId());
                    demandeRepos.deleteAllByProjetId(projet.getId());
                    chatRepos.deleteAllByConversationId(conversationRepos.findByProjet(projetService.findById(projet.getId())).getId());
                    conversationRepos.deleteAllByProjetId(projet.getId());
                    projetService.deleteProjet(projet.getId());
                }
                else {
                    projetService.removeUtilisateurFromProjet(projet.getId(),user.getId());
                }
            }
            System.out.println("PROJETS SUPPRIMÉS");
            Set<Utilisateur> followers = new HashSet<>(user.getFollowers());
            for (Utilisateur follower: followers){
                updateRelations(user.getId(),follower.getId());
            }
            System.out.println("FOLLOWERS RETIRÉS");
            Set<Utilisateur> followings = new HashSet<>(user.getFollowing());
            for (Utilisateur following: followings){
                updateRelations(following.getId(),user.getId());
            }
            System.out.println("FOLLOWINGS RETIRÉS");
            if (portfolioRepos.findByUtilisateur(user)!=null){
                portfolioRepos.delete(portfolioRepos.findByUtilisateur(user));
            }
            System.out.println("PORTFOLIO SUPPRIMÉ");
            List<Forum> forums = forumRepos.findAllByUtilisateur(user);
            for (Forum forum:forums){
                List<Commentaire> commentaires = commentaireRepos.findAllByForum(forum);
                commentaireRepos.deleteAll(commentaires);
                forumRepos.delete(forum);
            }
            System.out.println("FORUM SUPPRIMÉS");
            List<Commentaire> commentaires = commentaireRepos.findAllByUtilisateur(user);
            commentaireRepos.deleteAll(commentaires);
            System.out.println("COMMENTAIRES SUPPRIMÉS");
            repos.delete(user);
        }
    }

    private String encodePassword(String password) {
        return this.bCryptPasswordEncoder.encode(password);
    }

    private Notification prepareNotif(Utilisateur followed, Utilisateur follower){
        Notification notification = new Notification();
        notification.setLu(false);
        notification.setPseudoSender(follower.getPseudo());
        notification.setIdDest(followed.getId());
        notification.setImgSender(follower.getPhotoUrl());
        notification.setType("info");
        notification.setTitre("Nouvel abonné");
        notification.setMessage(follower.getPseudo() + " s'est abonné à votre compte");
        notification.setUrlNotif("/utilisateur/profil/"+follower.getPseudo());
        return notification;
    }
}

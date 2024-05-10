package com.artcorp.artsync.service.impl;

import com.artcorp.artsync.entity.Conversation;
import com.artcorp.artsync.entity.Notification;
import com.artcorp.artsync.entity.UserPrincipal;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.exception.domain.MauvaisIdentifiantException;
import com.artcorp.artsync.exception.domain.NotConnectedException;
import com.artcorp.artsync.repos.ConversationRepos;
import com.artcorp.artsync.repos.UtilisateurRepos;
import com.artcorp.artsync.service.UtilisateurService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class UtilisateurServiceImpl implements UtilisateurService, UserDetailsService {
    private UtilisateurRepos repos;
    private ConversationRepos conversationRepos;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public UtilisateurServiceImpl(UtilisateurRepos repos, ConversationRepos conversationRepos, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repos = repos;
        this.conversationRepos = conversationRepos;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur user = repos.findByPseudoAndActive(username);
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

        Utilisateur user = repos.findByPseudoAndActive(username);
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
    public Utilisateur findByPseudo(String pseudo) {
        return repos.findByPseudoAndActive(pseudo);
    }

    @Override
    public Utilisateur findByEmail(String email) {
        return repos.findByEmailAndActive(email);
    }

    @Override
    public Utilisateur findById(Long idUtilisateur) { return repos.findById(idUtilisateur).get(); }

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
        Utilisateur user = (Utilisateur) session.getAttribute("user");
        System.out.println(username);
        if(username.equalsIgnoreCase("anonymousUser")){
            throw new NotConnectedException("Veuiller vous connecter");
        }
        if(user == null){
            user = repos.findByPseudoAndActive(username);
            if (user == null){
                throw new NotConnectedException("Veuiller vous connecter");
            }
            session.setAttribute("user",user);
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
    public void delete(Long id) {
        repos.deleteById(id);
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
        notification.setTitre("Nouvelle abonné");
        notification.setMessage(follower.getPseudo() + " s'est abonné à votre compte");
        notification.setUrlNotif("/utilisateur/profil/"+follower.getPseudo());
        return notification;
    }
}

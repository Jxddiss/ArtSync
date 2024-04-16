package com.artcorp.artsync.service;


import com.artcorp.artsync.entity.Conversation;
import com.artcorp.artsync.entity.Projet;
import com.artcorp.artsync.entity.Utilisateur;

import java.util.List;

public interface ConversationService {

    List<Conversation> findByAllByUtilisateur(Utilisateur utilisateurUn);
    Conversation findByProjet(Projet projet);

    Conversation findById(Long id);
}

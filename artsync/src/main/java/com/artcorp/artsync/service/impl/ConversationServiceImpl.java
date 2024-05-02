package com.artcorp.artsync.service.impl;

import com.artcorp.artsync.entity.Conversation;
import com.artcorp.artsync.entity.Projet;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.repos.ConversationRepos;
import com.artcorp.artsync.service.ConversationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ConversationServiceImpl implements ConversationService {
    private ConversationRepos conversationRepos;

    @Autowired
    public ConversationServiceImpl(ConversationRepos conversationRepos) {
        this.conversationRepos = conversationRepos;
    }

    @Override
    public List<Conversation> findByAllByUtilisateur(Utilisateur utilisateur) {
        return conversationRepos.findByAllByUtilisateur(utilisateur);
    }

    @Override
    public Conversation findByProjet(Projet projet) {
        return conversationRepos.findByProjet(projet);
    }

    @Override
    public Conversation findById(Long id) {
        return conversationRepos.findById(id).isPresent() ? conversationRepos.findById(id).get() : null;
    }

    @Override
    public void createConvo(Conversation conversation) {
        conversationRepos.save(conversation);
    }
    @Override
    public void updateConvo(Conversation conversation){
        conversationRepos.save(conversation);
    }

    @Override
    public void deleteAllByProjetId(Long id) {
        conversationRepos.deleteAllByProjetId(id);
    }

    @Override
    public void deleteById(Long id) {
        conversationRepos.deleteById(id);
    }


}

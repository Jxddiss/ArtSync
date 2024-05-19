package com.artcorp.artsync.service;

import com.artcorp.artsync.entity.FichierGeneral;
import com.artcorp.artsync.entity.Projet;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.exception.domain.FileFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FichierService {

    FichierGeneral createFichier(FichierGeneral fichierGeneral);
    FichierGeneral createFichierProjet(FichierGeneral fichierGeneral, MultipartFile file) throws FileFormatException, IOException;
    public List<FichierGeneral> findAllByProjetAndUtilisateur(Projet projet, Utilisateur utilisateur);
    Long countByProjet(Projet projet);
    void deleteAllByProjet(Projet projet);
    void deleteById(Long id,String origin);
}

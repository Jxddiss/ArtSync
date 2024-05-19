package com.artcorp.artsync.service.impl;

import com.artcorp.artsync.entity.FichierGeneral;
import com.artcorp.artsync.entity.Projet;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.exception.domain.FileFormatException;
import com.artcorp.artsync.repos.FichierGeneralRepos;
import com.artcorp.artsync.service.FichierService;
import com.google.common.io.Files;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

import static com.artcorp.artsync.constant.FileConstant.ACCEPTED_FILE_EXTENSIONS;
import static com.artcorp.artsync.constant.FileConstant.FICHIER_GROUPE;
import static org.springframework.http.MediaType.*;

@Service
@Transactional
public class FichierServiceImpl implements FichierService {
    private final FichierGeneralRepos fichierGeneralRepository;

    @Autowired
    public FichierServiceImpl(FichierGeneralRepos fichierGeneralRepository) {
        this.fichierGeneralRepository = fichierGeneralRepository;
    }


    @Override
    public FichierGeneral createFichier(FichierGeneral fichierGeneral) {
        fichierGeneralRepository.save(fichierGeneral);
        return fichierGeneral;
    }

    @Override
    public FichierGeneral createFichierProjet(FichierGeneral fichierGeneral, MultipartFile file) throws FileFormatException, IOException {
        if(file.getContentType() != null){
            switch (file.getContentType()){
                case IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE:
                    fichierGeneral.setType("image");
                    break;
                case APPLICATION_PDF_VALUE:
                    fichierGeneral.setType("pdf");
                    break;
                default:
                    if (checkFileExtension(file)){
                        fichierGeneral.setType(Files.getFileExtension(file.getOriginalFilename()));
                    }else{
                        throw new FileFormatException("/groupe/group/"+fichierGeneral.getProjet().getId());
                    }
                    break;
            }
            saveFileInServer(file);
        }

        return fichierGeneralRepository.save(fichierGeneral);
    }

    @Override
    public List<FichierGeneral> findAllByProjetAndUtilisateur(Projet projet, Utilisateur utilisateur) {
        return fichierGeneralRepository.findAllByProjetAndUtilisateur(projet, utilisateur);
    }
    @Override
    public Long countByProjet(Projet projet) {
        return fichierGeneralRepository.countByProjet(projet);
    }
    @Override
    public void deleteAllByProjet(Projet projet) {
        fichierGeneralRepository.deleteAllByProjet(projet);
    }

    @Override
    public void deleteById(Long id) {
        fichierGeneralRepository.deleteById(id);
    }

    private boolean checkFileExtension(MultipartFile file){
        if (file.getOriginalFilename() != null){
            return Arrays.asList(ACCEPTED_FILE_EXTENSIONS)
                    .contains(Files.getFileExtension(file.getOriginalFilename()));
        }
        return false;
    }

    private void saveFileInServer(MultipartFile file) throws IOException {
        File parentDir = new File(FICHIER_GROUPE);
        File saveFile = new File(parentDir.getAbsolutePath() + File.separator + file.getOriginalFilename());

        if (!parentDir.exists()){
            parentDir.mkdirs();
        }

        java.nio.file.Files.copy(file.getInputStream(),saveFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        file.getInputStream().close();
    }
}

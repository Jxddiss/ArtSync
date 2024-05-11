package com.artcorp.artsync.repos;

import com.artcorp.artsync.entity.FichierGeneral;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class FichierGeneralReposTest {
    private final FichierGeneralRepos fichierGeneralRepos;

    @Autowired
    public FichierGeneralReposTest(FichierGeneralRepos fichierGeneralRepos) {
        this.fichierGeneralRepos = fichierGeneralRepos;
    }

    @Test
    public void setTypeFichier(){
        List<FichierGeneral> listFichier = fichierGeneralRepos.findAll();

        listFichier.forEach(fichierGeneral -> {
            fichierGeneral.setType("image");
            fichierGeneralRepos.save(fichierGeneral);
        });
    }
}
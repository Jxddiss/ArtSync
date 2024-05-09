package com.artcorp.artsync.repos;


import com.artcorp.artsync.entity.Utilisateur;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class UtilisateurReposTest {
    @Autowired
    private UtilisateurRepos repos;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void testCreateOneUtilisateur(){
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setPseudo("testy2");
        utilisateur.setPrenom("Test2Prenom");
        utilisateur.setNom("Test2Nom");
        utilisateur.setEmail("test2@test.com");
        utilisateur.setPassword("test");
        utilisateur.setPhotoUrl("default.png");
        utilisateur.setSpecialisation("test");
        utilisateur.setStatut("test");
        utilisateur.setActive(true);

        Utilisateur savedUser =  repos.save(utilisateur);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindOneUtilisateur(){
        Utilisateur utilisateur = repos.findById(1L).get();
        System.out.println(utilisateur);
        for (Utilisateur user1 : utilisateur.getFollowers()) {
            String s = "followers : " + user1;
            System.out.println(s);
        }
        for (Utilisateur user : utilisateur.getFollowing()) {
            System.out.println("following : "+ user);
        }
        assertThat(utilisateur).isNotNull();
    }

    @Test
    public void testSaveAmis(){
        Utilisateur utilisateur1 = repos.findById(1L).get();
        Utilisateur utilisateur2 = repos.findById(2L).get();
        utilisateur1.getAmis().add(utilisateur2);
        utilisateur2.getAmis().add(utilisateur1);
        repos.save(utilisateur1);
        repos.save(utilisateur2);
    }

    @Test
    public void testDeleteOneUtilisateur(){
        repos.deleteById(1L);
    }

    @Test
    public void testUpdateOneUtilisateur(){
        Utilisateur utilisateur = repos.findById(1L).get();
        utilisateur.setPseudo("testyUpdated");
        repos.save(utilisateur);
    }

    @Test
    public void testGetAllUtilisateurs(){
        Iterable<Utilisateur> utilisateurs = repos.findAll();
        for (Utilisateur utilisateur : utilisateurs) {
            System.out.println(utilisateur);
        }
    }

    @Test
    public void testSaveFollowers(){
        Utilisateur utilisateur1 = repos.findById(1L).get();
        Utilisateur utilisateur2 = repos.findById(2L).get();
        utilisateur1.getFollowers().add(utilisateur2);
        repos.save(utilisateur1);
    }

    @Test
    public void testSaveFollowing(){
        Utilisateur utilisateur1 = repos.findById(1L).get();
        Utilisateur utilisateur2 = repos.findById(2L).get();
        utilisateur1.getFollowing().add(utilisateur2);
        repos.save(utilisateur1);
    }

    @Test
    public void setRoles(){
        List<Utilisateur> utilisateurs = repos.findAll();
        utilisateurs.forEach(utilisateur -> {
            utilisateur.setRole("ROLE_USER");
            repos.save(utilisateur);
        });
    }

}
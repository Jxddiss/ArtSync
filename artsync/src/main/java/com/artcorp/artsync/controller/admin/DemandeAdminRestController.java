package com.artcorp.artsync.controller.admin;

import com.artcorp.artsync.dto.HttpResponse;
import com.artcorp.artsync.entity.DemandeAdmin;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.service.DemandeAdminService;
import com.artcorp.artsync.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DemandeAdminRestController {
    private final DemandeAdminService demandeAdminService;
    private final UtilisateurService utilisateurService;

    @Autowired
    public DemandeAdminRestController(DemandeAdminService demandeAdminService, UtilisateurService utilisateurService) {
        this.demandeAdminService = demandeAdminService;
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/api/demandes-admin")
    @PreAuthorize("hasAnyAuthority('user:update')")
    public ResponseEntity<List<DemandeAdmin>> getAllDemandeAdmin(){
        List<DemandeAdmin> listDemandeAdmin = demandeAdminService.findAll();
        return new ResponseEntity<>(listDemandeAdmin, HttpStatus.OK);
    }

    @PostMapping("/api/demandes-admin/envoyer")
    public String envoyerDemande(@RequestParam("email") String email,
                                 @RequestParam("message") String message){
        Utilisateur user = utilisateurService.findByEmail(email);
        if (user != null){
            DemandeAdmin demandeAdmin = new DemandeAdmin();
            demandeAdmin.setUserId(user.getId());
            demandeAdmin.setMessage(message);
            demandeAdmin = demandeAdminService.save(demandeAdmin);
            if (demandeAdmin == null){
                return "exist";
            }
            return "true";
        }
        return "false";
    }

    @DeleteMapping("/api/demandes-admin")
    @PreAuthorize("hasAnyAuthority('user:delete')")
    public ResponseEntity<HttpResponse> deleteDemande(@RequestBody DemandeAdmin demandeAdmin){
        demandeAdminService.delete(demandeAdmin);
        HttpResponse httpResponse = new HttpResponse(HttpStatus.OK.value(),
                HttpStatus.OK,HttpStatus.OK.getReasonPhrase(),"Demande supprimmé id : "+demandeAdmin.getId());
        return new ResponseEntity<>(httpResponse,HttpStatus.OK);
    }
}

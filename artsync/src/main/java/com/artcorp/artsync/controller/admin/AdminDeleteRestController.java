package com.artcorp.artsync.controller.admin;

import com.artcorp.artsync.dto.HttpResponse;
import com.artcorp.artsync.entity.Commentaire;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.service.CommentaireService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestController
public class AdminDeleteRestController {
    private CommentaireService commentaireService;

    @Autowired
    public AdminDeleteRestController(CommentaireService commentaireService) {
        this.commentaireService = commentaireService;
    }

    @DeleteMapping("/api/commentaires/delete")
    public ResponseEntity<HttpResponse> deleteCommentaire(@Param("commentaireId") Long commentaireId,
                                                    HttpMethod method) throws NoResourceFoundException {
        Commentaire commentaire = commentaireService.findById(commentaireId);

        if (commentaire!=null){
            commentaireService.deleteCommentaire(commentaire);
            HttpResponse httpResponse = new HttpResponse(HttpStatus.OK.value(),
                    HttpStatus.OK,HttpStatus.OK.getReasonPhrase(),"Success" );

            return new ResponseEntity<>(httpResponse, HttpStatus.OK);
        }
        throw new NoResourceFoundException(method,"Utilisateur id: "+commentaireId);
    }
}

package com.artcorp.artsync.controller.admin;

import com.artcorp.artsync.entity.UserPrincipal;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.enumeration.Role;
import com.artcorp.artsync.service.UtilisateurService;
import com.artcorp.artsync.utils.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.artcorp.artsync.constant.ExceptionConstant.MAUVAIS_IDENTIFIANTS;
import static com.artcorp.artsync.constant.SecurityConstant.JWT_TOKEN_HEADER;

@RestController
public class AdminAuthController {
    private final UtilisateurService utilisateurService;
    private final JWTTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AdminAuthController(UtilisateurService utilisateurService, JWTTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.utilisateurService = utilisateurService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/api/admin/login")
    public ResponseEntity<Utilisateur> loginAdmin(@RequestBody Utilisateur utilisateur){
        authenticate(utilisateur.getPseudo(),utilisateur.getPassword());
        Utilisateur user = utilisateurService.findByPseudo(utilisateur.getPseudo());
        if (!user.getRole().equalsIgnoreCase(Role.ROLE_ADMIN.name())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        UserPrincipal userPrincipal = new UserPrincipal(user);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(user,jwtHeader,HttpStatus.OK);
    }

    private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(userPrincipal));
        return headers;
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> mauvaisIdentifiants(){
        return new ResponseEntity<>(MAUVAIS_IDENTIFIANTS,HttpStatus.BAD_REQUEST);
    }
}

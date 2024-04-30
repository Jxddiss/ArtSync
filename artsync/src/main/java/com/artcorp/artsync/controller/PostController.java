package com.artcorp.artsync.controller;

import com.artcorp.artsync.entity.FichierGeneral;
import com.artcorp.artsync.entity.Post;
import com.artcorp.artsync.entity.Utilisateur;
import com.artcorp.artsync.exception.domain.NotConnectedException;
import com.artcorp.artsync.service.PostService;
import com.artcorp.artsync.service.impl.PostServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.artcorp.artsync.constant.FileConstant.USER_FOLDER;

@Controller
public class    PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @GetMapping("/explorer")
    public String explorer(HttpServletRequest request,
                           Model model) {
        HttpSession session = request.getSession(false);
        List<Post> postsEnVedette = null;
        List<Post> posts = null;
        if (session == null) {
            posts = postService.findByPubliqueEnVedette(true);
        }else{
            posts = postService.findAllPosts();
        }
        model.addAttribute("posts", posts);
        return "explorer";
    }

    @GetMapping("/feed")
    public String feed(HttpServletRequest request,
                       Model model,
                       RedirectAttributes redirectAttributes) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
            if (utilisateur != null) {
                List<Post> listPostsAbonnement = postService.findPostFollowing(utilisateur.getFollowing());
                model.addAttribute("utilisateur", utilisateur);
                model.addAttribute("listPosts", listPostsAbonnement);
                return "utilisateur/feed";
            }else{
                throw new NotConnectedException("Veuiller vous connecter");
            }

        }else{
            throw new Exception("Session nul");
        }
    }



    @PostMapping("/ajouter-post")
    public String ajouterPost(HttpServletRequest request,
                             @RequestParam("file") MultipartFile image,
                             @RequestParam("description") String texte,
                             @RequestParam("type") String type,
                             @RequestParam("titre") String titre,
                             @RequestParam("publique") boolean publique,
                             RedirectAttributes redirectAttributes) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
            if (image != null){

                String originalFilename = StringUtils.cleanPath(image.getOriginalFilename());
                FichierGeneral fichierGeneral = new FichierGeneral();
                fichierGeneral.setUrlMedia(originalFilename);

                Post post = new Post();
                post.setUtilisateur(utilisateur);
                post.setTitre("titre");
                post.setType("texte");
                post.setTexte(texte);
                post.setPublique(publique);
                post.setDate(LocalDateTime.now());
                post.setPseudoUtilisateur(utilisateur.getPseudo());

                fichierGeneral.setPost(post);

                HashSet<FichierGeneral> listFichiers = new HashSet<>();
                listFichiers.add(fichierGeneral);
                post.setListeFichiers(listFichiers);

                File parentDir = new File(USER_FOLDER);
                File saveFile = new File(parentDir.getAbsolutePath() + File.separator + originalFilename);
                Files.copy(image.getInputStream(),saveFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                postService.addPost(post);
            }
            return "redirect:/utilisateur/profil/" + utilisateur.getPseudo();
        }
        redirectAttributes.addFlashAttribute("error", "Veuillez vous connecter pour ajouter un post");
        return "redirect:/authentification";
    }

}

package com.artcorp.artsync.controller;

import com.artcorp.artsync.dto.ChatGPTRequest;
import com.artcorp.artsync.dto.ChatGPTResponse;
import com.artcorp.artsync.dto.Choice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class IdeaController {
    @Value("${openai.api.model}")
    private String model;
    @Value("${openai.api.url}")
    private String url;
    @Autowired
    private RestTemplate template;

    //Faudrait améliorer ce prompt pour qu'il soit plus précis et donne des résultat pertinents là maintenant il sort un peu n'importe quoi dépendamment du prompt
    //Regarder class ChatGPTRequest pour changer les settings
    private String rulePrompt =
            "Tu es un assistant virtuel qui aide les gens à trouver des idées. Tu NE peux ABSOLUMENT PAS utiliser plus de trois mots."+
            "Voici un exemple de conversation pour t'aider à démarrer:\n\n" +
            "Utilisateur: J'ai besoin d'une idée pour un projet de peinture. \n" +
            "Abstrait\n" +
            "Voilà le prompt de l'utilisateur: ";

    @GetMapping("/ideaPrompt")
    public String chat(@RequestParam(value = "prompt") String prompt, Model modelSent){
        ChatGPTRequest chatGPTRequest = new ChatGPTRequest(model, rulePrompt+prompt);
        ChatGPTResponse chatGPTResponse = template.postForObject(url, chatGPTRequest, ChatGPTResponse.class);
        List<Choice> choix = chatGPTResponse.getChoices();
        modelSent.addAttribute("listOutputs", choix);
        modelSent.addAttribute("promptWritten", prompt);
        return "boite-idee";
    }
}

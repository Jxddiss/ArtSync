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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private String rulePrompt = "Tu es un assistant créatif." +
            " Tu dois fournir des idées claires" +
            " et concises en réponse aux demandes" +
            " des utilisateurs. Réponds toujours" +
            " en fournissant exactement 12 éléments dans une liste." +
            " Chaque élément de la liste doit être court et ne pas dépasser 5 mots." +
            " Voici la requête de l'utilisateur : ";

    public static ArrayList<String> cleanText(String text) {
        ArrayList<String> parts = new ArrayList<>();
        Pattern pattern = Pattern.compile("(\\d+\\.)");
        Matcher matcher = pattern.matcher(text);

        int lastIndex = 0;

        while (matcher.find()) {
            int currentIndex = matcher.start();
            if (currentIndex > lastIndex) {
                parts.add(text.substring(lastIndex, currentIndex).trim());
            }
            lastIndex = matcher.end();
        }

        if (lastIndex < text.length()) {
            parts.add(text.substring(lastIndex).trim());
        }

        return parts;
    }
    @GetMapping("/ideaPrompt")
    public String chat(@RequestParam(value = "prompt") String prompt, Model modelSent){
        ChatGPTRequest chatGPTRequest = new ChatGPTRequest(model, rulePrompt+prompt);
        ChatGPTResponse chatGPTResponse = template.postForObject(url, chatGPTRequest, ChatGPTResponse.class);
        List<String> choix = cleanText(chatGPTResponse.getChoices().get(0).getText());
        choix.remove(choix.get(0));
        System.out.println(choix);
        modelSent.addAttribute("listOutputs", choix);
        modelSent.addAttribute("promptWritten", prompt);
        return "boite-idee";
    }
}

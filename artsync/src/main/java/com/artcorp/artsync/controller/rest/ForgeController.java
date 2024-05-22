package com.artcorp.artsync.controller.rest;
import com.artcorp.artsync.configuration.ApiConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Arrays;
import java.util.Base64;

@RestController
public class ForgeController {

    @Autowired
    private ApiConfig apiConfig;

    @PostMapping("/generate")
    public ResponseEntity<String> generateImage(@RequestParam("prompt") String prompt) {
        //lien api
        String apiUrl = "https://api.stability.ai/v2beta/stable-image/generate/sd3";

        // header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiConfig.getApiKey());
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setAccept(Arrays.asList(MediaType.parseMediaType("image/*"), MediaType.APPLICATION_JSON));

        // body
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("mode", "text-to-image");
        body.add("prompt", prompt);
        body.add("aspect_ratio", "1:1");
        body.add("output_format", "png");
        body.add("model", "sd3");

        // request
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        //appel à l'api + retour
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, byte[].class);

        if (response.getStatusCodeValue() == 200) {
            //envoie de l'image
            String base64EncodedImage = Base64.getEncoder().encodeToString(response.getBody());
            return ResponseEntity.ok().body(base64EncodedImage);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while generating image.");
        }
    }
}
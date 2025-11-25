package net.boulaalam.eda.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import lombok.RequiredArgsConstructor;

@Controller // Renvoie du HTML
@RequiredArgsConstructor
public class WebClickController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC_NAME = "clicks";

    // 1. Affiche la page avec le bouton
    @GetMapping("/")
    public String index() {
        return "index"; // Cherche index.html dans resources/templates
    }

    // 2. Traite le clic et envoie à Kafka
    @PostMapping("/click")
    public String recordClick() {
        String userId = "user-" + (int)(Math.random() * 1000); // Simule un ID utilisateur

        // Envoi du message : Clé = userId, Valeur = "CLICK"
        kafkaTemplate.send(TOPIC_NAME, userId, "CLICK");

        System.out.println("Message envoyé pour : " + userId);
        return "redirect:/"; // Recharge la page
    }
}
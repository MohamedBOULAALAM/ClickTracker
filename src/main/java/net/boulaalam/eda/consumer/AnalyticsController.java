package net.boulaalam.eda.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class AnalyticsController {

    // Simule une base de données en mémoire pour stocker les derniers comptes reçus
    private final Map<String, Long> userClickCounts = new ConcurrentHashMap<>();

    // 1. Écoute les résultats du Stream (Topic: click-counts)
    @KafkaListener(topics = "click-counts", groupId = "realtime-dashboard-group")
    public void consume(org.apache.kafka.clients.consumer.ConsumerRecord<String, Long> record) {
        // key = userId, value = total clicks
        System.out.println("Mise à jour reçue : " + record.key() + " = " + record.value());
        if (record.key() != null) {
            userClickCounts.put(record.key(), record.value());
        }
    }

    // 2. Endpoint API pour récupérer le total global
    @GetMapping("/clicks/count")
    public Map<String, Object> getTotalCounts() {
        long totalClicks = userClickCounts.values().stream().mapToLong(Long::longValue).sum();

        return Map.of(
                "total_global_clicks", totalClicks,
                "details_by_user", userClickCounts
        );
    }
}
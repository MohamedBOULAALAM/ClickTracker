package net.boulaalam.eda.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic clicksTopic() {
        return TopicBuilder.name("clicks")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic countsTopic() {
        return TopicBuilder.name("click-counts")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
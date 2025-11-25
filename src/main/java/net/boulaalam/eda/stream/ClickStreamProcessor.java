package net.boulaalam.eda.stream;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@Configuration
@EnableKafkaStreams
public class ClickStreamProcessor {

    @Bean
    public KStream<String, String> kStream(StreamsBuilder builder) {
        // 1. Lire le topic "clicks"
        KStream<String, String> sourceStream = builder.stream("clicks");

        // 2. Traitement : Compter par clé (userId)
        // input: userId -> "CLICK"
        KTable<String, Long> counts = sourceStream
                .groupByKey()
                .count();

        // 3. Écrire le résultat dans "click-counts"
        // Le résultat du count() est un Long, donc on doit spécifier le Serde Long pour la valeur
        counts.toStream()
                .to("click-counts", Produced.with(Serdes.String(), Serdes.Long()));

        return sourceStream;
    }
}
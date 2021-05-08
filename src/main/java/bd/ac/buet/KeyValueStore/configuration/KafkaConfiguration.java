package bd.ac.buet.KeyValueStore.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.Collections;

@Configuration
@Slf4j
public class KafkaConfiguration {
    private final KafkaProperties kafkaProperties;

    public KafkaConfiguration(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    KafkaSender<Integer, String> kafkaSender(){
        SenderOptions<Integer, String> senderOptions = SenderOptions.<Integer, String>create(kafkaProperties.getProducerProps())
                .maxInFlight(1024);
        return  KafkaSender.create(senderOptions);
    }

    @Bean
    KafkaReceiver<Integer, String> kafkaReceiver(){
        ReceiverOptions<Integer, String> receiverOptions = ReceiverOptions.<Integer, String>create(kafkaProperties.getConsumerProps())
                .subscription(Collections.singleton("topic"));
        return KafkaReceiver.create(receiverOptions);
    }
}

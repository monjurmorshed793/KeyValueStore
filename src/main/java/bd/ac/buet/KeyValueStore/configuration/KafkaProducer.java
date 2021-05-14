package bd.ac.buet.KeyValueStore.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaProducer {
    private final KafkaProperties kafkaProperties;
    private final KafkaTemplate kafkaTemplate;

    public KafkaProducer(KafkaProperties kafkaProperties, KafkaTemplate kafkaTemplate) {
        this.kafkaProperties = kafkaProperties;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, String payload){
      log.debug("Sending payload for topic {}", topic);
      kafkaTemplate.send(topic, payload);
    }
}

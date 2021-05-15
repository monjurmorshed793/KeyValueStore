package bd.ac.buet.KeyValueStore.configuration;

import bd.ac.buet.KeyValueStore.model.ServerInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, Object payload){
      log.debug("Sending payload for topic {}", topic);
      this.kafkaTemplate.send(topic, payload);
    }
}

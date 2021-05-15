package bd.ac.buet.KeyValueStore.configuration;

import bd.ac.buet.KeyValueStore.model.ServerInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
public class KafkaConsumer {
    private CountDownLatch latch = new CountDownLatch(1);
    private ServerInfo payload = null;

    @KafkaListener(topics = "${application.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void receive(ServerInfo consumerRecord) {
        payload = consumerRecord;
        latch.countDown();
    }


    public CountDownLatch getLatch() {
        return latch;
    }

    public ServerInfo getPayload() {
        return payload;
    }
}

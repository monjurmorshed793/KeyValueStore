package bd.ac.buet.KeyValueStore.service;

import bd.ac.buet.KeyValueStore.model.ServerInfo;
import bd.ac.buet.KeyValueStore.repository.ServerInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;

@Transactional
@Service
@Slf4j
public class KafkaConsumer {
    private CountDownLatch latch = new CountDownLatch(1);
    private ServerInfo payload = null;

    private final ServerInfoService serverInfoService;

    public KafkaConsumer(ServerInfoService serverInfoService) {
        this.serverInfoService = serverInfoService;
    }

    @KafkaListener(topics = "${application.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void receive(ServerInfo consumerRecord) {
        payload = consumerRecord;
        serverInfoService.storeServerInfo(payload);
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public ServerInfo getPayload() {
        return payload;
    }

}

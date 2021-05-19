package bd.ac.buet.KeyValueStore.service;

import bd.ac.buet.KeyValueStore.dto.ProposerResponseDTO;
import bd.ac.buet.KeyValueStore.model.ServerInfo;
import bd.ac.buet.KeyValueStore.model.TempData;
import bd.ac.buet.KeyValueStore.service.paxos.ProposerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
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
    private final ProposerService proposerService;
    private final KafkaProducer kafkaProducer;

    public KafkaConsumer(ServerInfoService serverInfoService, ProposerService proposerService, KafkaProducer kafkaProducer) {
        this.serverInfoService = serverInfoService;
        this.proposerService = proposerService;
        this.kafkaProducer = kafkaProducer;
    }

    @KafkaListener(topics = "${application.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void receive(ServerInfo consumerRecord) {
        payload = consumerRecord;
        serverInfoService.storeServerInfo(payload);
        latch.countDown();
    }

    @KafkaListener(topics = "proposer-request", groupId = "${spring.kafka.consumer.group-id}")
    public void proposerRequest(TempData tempData){
        proposerService.responseToProposer(tempData);
    }

    @KafkaListener(topics = "proposer-response", groupId = "${spring.kafka.consumer.group-id}")
    public void receiveProposerResponse(ProposerResponseDTO proposerResponse){
        proposerService.processProposerResponse(proposerResponse);
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public ServerInfo getPayload() {
        return payload;
    }

}

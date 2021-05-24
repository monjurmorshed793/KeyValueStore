package bd.ac.buet.KeyValueStore.service;

import bd.ac.buet.KeyValueStore.dto.LearnerRequestDto;
import bd.ac.buet.KeyValueStore.dto.ProposerResponseDTO;
import bd.ac.buet.KeyValueStore.model.ServerInfo;
import bd.ac.buet.KeyValueStore.model.TempData;
import bd.ac.buet.KeyValueStore.service.paxos.AcceptorService;
import bd.ac.buet.KeyValueStore.service.paxos.LearnerService;
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
    private final AcceptorService acceptorService;
    private final LearnerService learnerService;

    public KafkaConsumer(ServerInfoService serverInfoService, ProposerService proposerService, KafkaProducer kafkaProducer, AcceptorService acceptorService, LearnerService learnerService) {
        this.serverInfoService = serverInfoService;
        this.proposerService = proposerService;
        this.kafkaProducer = kafkaProducer;
        this.acceptorService = acceptorService;
        this.learnerService = learnerService;
    }

    @KafkaListener(topics = "server-info", groupId = "${spring.kafka.consumer.group-id}")
    public void receive(ServerInfo consumerRecord) {
        payload = consumerRecord;
        serverInfoService.storeServerInfo(payload);
        //latch.countDown();
    }

    @KafkaListener(topics = "proposer-request", groupId = "${spring.kafka.consumer.group-id}")
    public void proposerRequest(TempData tempData){
        proposerService.responseToProposer(tempData);
    }

    @KafkaListener(topics = "proposer-response", groupId = "${spring.kafka.consumer.group-id}")
    public void receiveProposerResponse(ProposerResponseDTO proposerResponse){
        proposerService.processProposerResponse(proposerResponse);
    }

    @KafkaListener(topics = "acceptor-request", groupId = "${spring.kafka.consumer.group-id}")
    public void acceptorRequest(TempData tempData){
        acceptorService.processAcceptorRequest(tempData);
    }

    @KafkaListener(topics = "learner-request", groupId = "${spring.kafka.consumer.group-id}")
    public void learnerRequest(LearnerRequestDto learnerRequest){
        learnerService.processLearnerRequest(learnerRequest);
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public ServerInfo getPayload() {
        return payload;
    }

}

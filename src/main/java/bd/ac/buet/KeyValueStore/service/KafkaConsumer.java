package bd.ac.buet.KeyValueStore.service;

import bd.ac.buet.KeyValueStore.dto.LearnerRequestDto;
import bd.ac.buet.KeyValueStore.dto.ProposerResponseDTO;
import bd.ac.buet.KeyValueStore.dto.RestoreDTO;
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
    private final ObjectStoreService objectStoreService;

    public KafkaConsumer(ServerInfoService serverInfoService, ProposerService proposerService, KafkaProducer kafkaProducer, AcceptorService acceptorService, LearnerService learnerService, ObjectStoreService objectStoreService) {
        this.serverInfoService = serverInfoService;
        this.proposerService = proposerService;
        this.kafkaProducer = kafkaProducer;
        this.acceptorService = acceptorService;
        this.learnerService = learnerService;
        this.objectStoreService = objectStoreService;
    }

    @KafkaListener(topics = "server-info", groupId = "${spring.kafka.consumer.group-id}")
    public void receive(ServerInfo consumerRecord) {
        payload = consumerRecord;
        log.info("Storing server info");
        serverInfoService.storeServerInfo(payload);
        //latch.countDown();
    }

    @KafkaListener(topics = "proposer-request", groupId = "${spring.kafka.consumer.group-id}")
    public void proposerRequest(TempData tempData){
        log.info("Proposer request");
        proposerService.responseToProposer(tempData);
    }

    @KafkaListener(topics = "proposer-response", groupId = "${spring.kafka.consumer.group-id}")
    public void receiveProposerResponse(ProposerResponseDTO proposerResponse){
        log.info("Proposer response");
        proposerService.processProposerResponse(proposerResponse);
    }

    @KafkaListener(topics = "acceptor-request", groupId = "${spring.kafka.consumer.group-id}")
    public void acceptorRequest(TempData tempData){
        log.info("Acceptor request");
        acceptorService.processAcceptorRequest(tempData);
    }

    @KafkaListener(topics = "learner-request", groupId = "${spring.kafka.consumer.group-id}")
    public void learnerRequest(LearnerRequestDto learnerRequest){
        log.info("Learner Request");
        learnerService.processLearnerRequest(learnerRequest);
    }

    @KafkaListener(topics = "restore", groupId = "${spring.kafka.consumer.group-id}")
    public void restoreResponse(RestoreDTO restoreDTO){
        if(restoreDTO.getServerInfo().getName().equals(serverInfoService.getSelfServerInfo().getName())){
            objectStoreService.restoreObjectStores(restoreDTO.getObjectStores());
            objectStoreService.deleteObjectStores(restoreDTO.getDeleteIds());
        }
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public ServerInfo getPayload() {
        return payload;
    }

}

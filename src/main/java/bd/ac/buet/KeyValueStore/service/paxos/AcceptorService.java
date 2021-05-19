package bd.ac.buet.KeyValueStore.service.paxos;

import bd.ac.buet.KeyValueStore.dto.LearnerRequestDto;
import bd.ac.buet.KeyValueStore.model.ServerInfo;
import bd.ac.buet.KeyValueStore.model.TempData;
import bd.ac.buet.KeyValueStore.service.KafkaProducer;
import bd.ac.buet.KeyValueStore.service.LearnerStoreService;
import bd.ac.buet.KeyValueStore.service.ServerInfoService;
import org.springframework.stereotype.Service;

@Service
public class AcceptorService {
    private final LearnerStoreService learnerStoreService;
    private final ServerInfoService serverInfoService;
    private final KafkaProducer kafkaProducer;

    public AcceptorService(LearnerStoreService learnerStoreService, ServerInfoService serverInfoService, KafkaProducer kafkaProducer) {
        this.learnerStoreService = learnerStoreService;
        this.serverInfoService = serverInfoService;
        this.kafkaProducer = kafkaProducer;
    }

    public void processAcceptorRequest(TempData tempData){
        learnerStoreService.createLearnerStore(tempData);
    }

    public void sendLearnerRequest(TempData tempData){
        ServerInfo selfServerInfo = serverInfoService.getSelfServerInfo();
        LearnerRequestDto learnerRequestDto = LearnerRequestDto
                .builder()
                .serverInfo(selfServerInfo)
                .tempData(tempData)
                .build();
        kafkaProducer.send("learner-request", learnerRequestDto);
    }
}

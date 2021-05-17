package bd.ac.buet.KeyValueStore.service.paxos;

import bd.ac.buet.KeyValueStore.dto.ProposerResponseDTO;
import bd.ac.buet.KeyValueStore.model.ObjectStore;
import bd.ac.buet.KeyValueStore.model.ServerInfo;
import bd.ac.buet.KeyValueStore.model.TempData;
import bd.ac.buet.KeyValueStore.repository.ObjectStoreRepository;
import bd.ac.buet.KeyValueStore.service.*;
import org.springframework.stereotype.Service;

@Service
public class ProposerService {
    private final TempDataService tempDataService;
    private final ObjectStoreService objectStoreService;
    private final KafkaProducer kafkaProducer;
    private final ObjectStoreRepository objectStoreRepository;
    private final ServerInfoService serverInfoService;
    private final PaxosStoreService paxosStoreService;

    public ProposerService(TempDataService tempDataService, ObjectStoreService objectStoreService, KafkaProducer kafkaProducer, ObjectStoreRepository objectStoreRepository, ServerInfoService serverInfoService, PaxosStoreService paxosStoreService) {
        this.tempDataService = tempDataService;
        this.objectStoreService = objectStoreService;
        this.kafkaProducer = kafkaProducer;
        this.objectStoreRepository = objectStoreRepository;
        this.serverInfoService = serverInfoService;
        this.paxosStoreService = paxosStoreService;
    }

    public ObjectStore propose(ObjectStore objectStore){
        TempData tempData = objectStoreService.convertToTempData(objectStore);
        tempData = tempDataService.save(tempData);
        paxosStoreService.createInitialPaxosStore(tempData);
        kafkaProducer.send("proposer-request", tempData);
        return tempDataService.convertToObjectStore(tempData);
    }

    public void responseToProposer(TempData tempData){
        ProposerResponseDTO proposerResponse = new ProposerResponseDTO();
        ServerInfo selfServerInfo = serverInfoService.getSelfServerInfo();
        proposerResponse.setServerInfo(selfServerInfo);
        if(objectStoreRepository.existsById(tempData.getObjectId())){
            TempData existingData = objectStoreService.convertToTempData(objectStoreRepository.findById(tempData.getObjectId()).get());
            existingData.setProposedBy(tempData.getProposedBy());
            proposerResponse.setTempData(existingData);
        }else{
            proposerResponse.setTempData(tempData);
        }
        kafkaProducer.send("proposer-response", proposerResponse);
    }

    public void processProposerResponse(ProposerResponseDTO proposerResponse){
        ServerInfo selfServerInfo = serverInfoService.getSelfServerInfo();
        if(proposerResponse.getTempData().getProposedBy().getId().equals(selfServerInfo.getId())){

        }
    }
}

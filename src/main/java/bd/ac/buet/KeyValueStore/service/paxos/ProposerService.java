package bd.ac.buet.KeyValueStore.service.paxos;

import bd.ac.buet.KeyValueStore.dto.ProposerResponseDTO;
import bd.ac.buet.KeyValueStore.model.ObjectStore;
import bd.ac.buet.KeyValueStore.model.ServerInfo;
import bd.ac.buet.KeyValueStore.model.TempData;
import bd.ac.buet.KeyValueStore.repository.ObjectStoreRepository;
import bd.ac.buet.KeyValueStore.repository.TempDataRepository;
import bd.ac.buet.KeyValueStore.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProposerService {
    private final TempDataService tempDataService;
    private final ObjectStoreService objectStoreService;
    private final KafkaProducer kafkaProducer;
    private final ObjectStoreRepository objectStoreRepository;
    private final ServerInfoService serverInfoService;
    private final ProposerStoreService proposerStoreService;
    private final DetailedProposerStoreService detailedProposerStoreService;
    private final TempDataRepository tempDataRepository;

    public ProposerService(TempDataService tempDataService, ObjectStoreService objectStoreService, KafkaProducer kafkaProducer, ObjectStoreRepository objectStoreRepository, ServerInfoService serverInfoService, ProposerStoreService proposerStoreService, DetailedProposerStoreService detailedProposerStoreService, TempDataRepository tempDataRepository) {
        this.tempDataService = tempDataService;
        this.objectStoreService = objectStoreService;
        this.kafkaProducer = kafkaProducer;
        this.objectStoreRepository = objectStoreRepository;
        this.serverInfoService = serverInfoService;
        this.proposerStoreService = proposerStoreService;
        this.detailedProposerStoreService = detailedProposerStoreService;
        this.tempDataRepository = tempDataRepository;
    }

    public ObjectStore propose(ObjectStore objectStore){
        TempData tempData = objectStoreService.convertToTempData(objectStore);
        tempData = tempDataService.save(tempData);
        proposerStoreService.createInitialProposerStore(tempData);
        kafkaProducer.send("proposer-request", tempData);
        return tempDataService.convertToObjectStore(tempData);
    }

    public void responseToProposer(TempData tempData){
        if(!tempDataRepository.existsById(tempData.getId())){
            tempDataRepository.save(tempData);
        }
        ProposerResponseDTO proposerResponse = new ProposerResponseDTO();
        ServerInfo selfServerInfo = serverInfoService.getSelfServerInfo();
        proposerResponse.setServerInfo(selfServerInfo);
        if(!selfServerInfo.getId().equals(tempData.getProposedBy().getId()) && objectStoreRepository.existsById(tempData.getObjectId())){
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
            detailedProposerStoreService.updateDetailedProposerStoreOnProposalResponse(proposerResponse);
        }
    }
}

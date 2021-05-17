package bd.ac.buet.KeyValueStore.service;

import bd.ac.buet.KeyValueStore.dto.ProposerResponseDTO;
import bd.ac.buet.KeyValueStore.model.DetailedPaxosStore;
import bd.ac.buet.KeyValueStore.model.PaxosStore;
import bd.ac.buet.KeyValueStore.model.ServerInfo;
import bd.ac.buet.KeyValueStore.model.enumeration.State;
import bd.ac.buet.KeyValueStore.model.enumeration.Status;
import bd.ac.buet.KeyValueStore.repository.DetailedPaxosStoreRepository;
import bd.ac.buet.KeyValueStore.repository.ServerInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class DetailedPaxosStoreService {
    private final DetailedPaxosStoreRepository detailedPaxosStoreRepository;
    private final ServerInfoRepository serverInfoRepository;
    private final PaxosStoreService paxosStoreService;

    public DetailedPaxosStoreService(DetailedPaxosStoreRepository detailedPaxosStoreRepository, ServerInfoRepository serverInfoRepository, PaxosStoreService paxosStoreService) {
        this.detailedPaxosStoreRepository = detailedPaxosStoreRepository;
        this.serverInfoRepository = serverInfoRepository;
        this.paxosStoreService = paxosStoreService;
    }

    public void createInitialDetailedPaxosStore(PaxosStore paxosStore){
        Iterator<ServerInfo> serverInfoIterator = serverInfoRepository.findAll().iterator();
        List<DetailedPaxosStore> detailedPaxosStores = new ArrayList<>();
        while(serverInfoIterator.hasNext()){
            ServerInfo serverInfo = serverInfoIterator.next();
            DetailedPaxosStore detailedPaxosStore  = DetailedPaxosStore
                    .builder()
                    .paxosStore(paxosStore)
                    .serverInfo(serverInfo)
                    .state(State.PROPOSER_REQUESTED)
                    .status(Status.IN_PROCESS)
                    .createdOn(Instant.now())
                    .updatedOn(Instant.now())
                    .build();
            detailedPaxosStores.add(detailedPaxosStore);
        }
        detailedPaxosStoreRepository.saveAll(detailedPaxosStores);
    }

    public void updateDetailedPaxosStoreOnProposalResponse(ProposerResponseDTO proposerResponse){
        DetailedPaxosStore detailedPaxosStore = detailedPaxosStoreRepository.findByPaxosStore_TempData_IdAndServerInfo_Id(proposerResponse.getTempData().getId(), proposerResponse.getServerInfo().getId()).get();
        detailedPaxosStore.setRespondedTempData(proposerResponse.getTempData());
        detailedPaxosStore.setState(State.PROPOSER_RESPONDED);
        detailedPaxosStore.setUpdatedOn(Instant.now());
        detailedPaxosStoreRepository.save(detailedPaxosStore);
        paxosStoreService.updatedPaxosStoreStatus(detailedPaxosStore.getPaxosStore());
    }
}

package bd.ac.buet.KeyValueStore.service;

import bd.ac.buet.KeyValueStore.dto.ProposerResponseDTO;
import bd.ac.buet.KeyValueStore.model.DetailedProposerStore;
import bd.ac.buet.KeyValueStore.model.ProposerStore;
import bd.ac.buet.KeyValueStore.model.ServerInfo;
import bd.ac.buet.KeyValueStore.model.enumeration.State;
import bd.ac.buet.KeyValueStore.model.enumeration.Status;
import bd.ac.buet.KeyValueStore.repository.DetailedProposerStoreRepository;
import bd.ac.buet.KeyValueStore.repository.ServerInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class DetailedProposerStoreService {
    private final DetailedProposerStoreRepository detailedProposerStoreRepository;
    private final ServerInfoRepository serverInfoRepository;
    private final ProposerStoreService proposerStoreService;

    public DetailedProposerStoreService(DetailedProposerStoreRepository detailedProposerStoreRepository, ServerInfoRepository serverInfoRepository, ProposerStoreService proposerStoreService) {
        this.detailedProposerStoreRepository = detailedProposerStoreRepository;
        this.serverInfoRepository = serverInfoRepository;
        this.proposerStoreService = proposerStoreService;
    }

    public void createInitialDetailedProposerStore(ProposerStore proposerStore){
        Iterator<ServerInfo> serverInfoIterator = serverInfoRepository.findAll().iterator();
        List<DetailedProposerStore> detailedProposerStores = new ArrayList<>();
        while(serverInfoIterator.hasNext()){
            ServerInfo serverInfo = serverInfoIterator.next();
            DetailedProposerStore detailedProposerStore = DetailedProposerStore
                    .builder()
                    .proposerStore(proposerStore)
                    .serverInfo(serverInfo)
                    .state(State.PROPOSER_REQUESTED)
                    .status(Status.IN_PROCESS)
                    .createdOn(Instant.now())
                    .updatedOn(Instant.now())
                    .build();
            detailedProposerStores.add(detailedProposerStore);
        }
        detailedProposerStoreRepository.saveAll(detailedProposerStores);
    }

    public void updateDetailedProposerStoreOnProposalResponse(ProposerResponseDTO proposerResponse){
        DetailedProposerStore detailedProposerStore = detailedProposerStoreRepository.findByPaxosStore_TempData_IdAndServerInfo_Id(proposerResponse.getTempData().getId(), proposerResponse.getServerInfo().getId()).get();
        detailedProposerStore.setRespondedTempData(proposerResponse.getTempData());
        detailedProposerStore.setState(State.PROPOSER_RESPONDED);
        detailedProposerStore.setUpdatedOn(Instant.now());
        detailedProposerStoreRepository.save(detailedProposerStore);
        proposerStoreService.updatedProposerStoreStatus(detailedProposerStore.getProposerStore());
    }
}

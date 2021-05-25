package bd.ac.buet.KeyValueStore.service;

import bd.ac.buet.KeyValueStore.dto.ProposerResponseDTO;
import bd.ac.buet.KeyValueStore.model.DetailedProposerStore;
import bd.ac.buet.KeyValueStore.model.ProposerStore;
import bd.ac.buet.KeyValueStore.model.ServerInfo;
import bd.ac.buet.KeyValueStore.model.enumeration.State;
import bd.ac.buet.KeyValueStore.model.enumeration.Status;
import bd.ac.buet.KeyValueStore.repository.DetailedProposerStoreRepository;
import bd.ac.buet.KeyValueStore.repository.ProposerStoreRepository;
import bd.ac.buet.KeyValueStore.repository.ServerInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.context.annotation.Lazy;
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
    private final ProposerStoreRepository proposerStoreRepository;

    public DetailedProposerStoreService(DetailedProposerStoreRepository detailedProposerStoreRepository, ServerInfoRepository serverInfoRepository, @Lazy ProposerStoreService proposerStoreService,@Lazy ProposerStoreRepository proposerStoreRepository) {
        this.detailedProposerStoreRepository = detailedProposerStoreRepository;
        this.serverInfoRepository = serverInfoRepository;
        this.proposerStoreService = proposerStoreService;
        this.proposerStoreRepository = proposerStoreRepository;
    }

    public void createInitialDetailedProposerStore(ProposerStore proposerStore){
        List<ServerInfo> serverInfoIterator = IteratorUtils.toList(serverInfoRepository.findAll().iterator());
        List<DetailedProposerStore> detailedProposerStores = new ArrayList<>();
        for(ServerInfo serverInfo: serverInfoIterator){
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
        Iterator<DetailedProposerStore> detailedProposerStoreIterator =  detailedProposerStoreRepository.saveAll(detailedProposerStores).iterator();
        log.info("total detailed proposer stores created: ");
        log.info(IteratorUtils.toList(detailedProposerStoreIterator).size()+"");
    }

    public void updateDetailedProposerStoreOnProposalResponse(ProposerResponseDTO proposerResponse){
        DetailedProposerStore detailedProposerStore = detailedProposerStoreRepository.findByProposerStoreTempDataIdAndServerInfoId(proposerResponse.getTempData().getId(), proposerResponse.getServerInfo().getId()).get();
        detailedProposerStore.setRespondedTempData(proposerResponse.getTempData());
        detailedProposerStore.setState(State.PROPOSER_RESPONDED);
        detailedProposerStore.setUpdatedOn(Instant.now());
        detailedProposerStoreRepository.save(detailedProposerStore);
        proposerStoreService.updatedProposerStoreStatus(detailedProposerStore.getProposerStore());
    }
}

package bd.ac.buet.KeyValueStore.service;

import bd.ac.buet.KeyValueStore.model.DetailedProposerStore;
import bd.ac.buet.KeyValueStore.model.ProposerStore;
import bd.ac.buet.KeyValueStore.model.TempData;
import bd.ac.buet.KeyValueStore.model.enumeration.State;
import bd.ac.buet.KeyValueStore.model.enumeration.Status;
import bd.ac.buet.KeyValueStore.repository.DetailedProposerStoreRepository;
import bd.ac.buet.KeyValueStore.repository.ProposerStoreRepository;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ProposerStoreService {
    private final ProposerStoreRepository proposerStoreRepository;
    private final DetailedProposerStoreService detailedProposerStoreService;
    private final DetailedProposerStoreRepository detailedProposerStoreRepository;

    public ProposerStoreService(ProposerStoreRepository proposerStoreRepository, DetailedProposerStoreService detailedProposerStoreService, DetailedProposerStoreRepository detailedProposerStoreRepository) {
        this.proposerStoreRepository = proposerStoreRepository;
        this.detailedProposerStoreService = detailedProposerStoreService;
        this.detailedProposerStoreRepository = detailedProposerStoreRepository;
    }

    public void createInitialProposerStore(TempData tempData){
        ProposerStore proposerStore = ProposerStore
                .builder()
                .tempData(tempData)
                .state(State.PROPOSER_REQUESTED)
                .status(Status.IN_PROCESS)
                .createdOn(Instant.now())
                .updatedOn(Instant.now())
                .build();
        proposerStore = proposerStoreRepository.save(proposerStore);
        detailedProposerStoreService.createInitialDetailedProposerStore(proposerStore);
    }

    public void updatedProposerStoreStatus(ProposerStore proposerStore){
        List<DetailedProposerStore> detailedProposerStores = IteratorUtils.toList((detailedProposerStoreRepository.findAllByPaxosStore_Id(proposerStore.getId())));
        ProposerStore latestProposerStore = proposerStoreRepository.findById(proposerStore.getId()).get();
        if(latestProposerStore.getState().equals(State.PROPOSER_REQUESTED)){
            long totalProposerResponded = detailedProposerStores.stream().filter(d-> d.getState().equals(State.PROPOSER_RESPONDED)).count();
            long totalServers = detailedProposerStores.stream().count();
            if(totalServers==totalProposerResponded){
                TempData latestTempData = detailedProposerStores
                        .stream()
                        .map(d-> d.getRespondedTempData())
                        .sorted((o1,o2)-> o1.getUpdatedOn().compareTo(o2.getUpdatedOn()))
                        .findFirst().get();
                if(latestTempData.getId().equals(proposerStore.getTempData().getId())){
                    latestProposerStore.setState(State.ACCEPTOR_REQUESTED);
                    latestProposerStore.setStatus(Status.ACCEPTED);
                    // todo create acceptor service
                }else{
                    latestProposerStore.setState(State.PROPOSER_RESPONDED);
                    latestProposerStore.setStatus(Status.REJECTED);
                }
                latestProposerStore.setUpdatedOn(Instant.now());
                proposerStoreRepository.save(latestProposerStore);
            }
        }
    }
}

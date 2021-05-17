package bd.ac.buet.KeyValueStore.service;

import bd.ac.buet.KeyValueStore.model.DetailedPaxosStore;
import bd.ac.buet.KeyValueStore.model.PaxosStore;
import bd.ac.buet.KeyValueStore.model.TempData;
import bd.ac.buet.KeyValueStore.model.enumeration.State;
import bd.ac.buet.KeyValueStore.model.enumeration.Status;
import bd.ac.buet.KeyValueStore.repository.DetailedPaxosStoreRepository;
import bd.ac.buet.KeyValueStore.repository.PaxosStoreRepository;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
public class PaxosStoreService {
    private final PaxosStoreRepository paxosStoreRepository;
    private final DetailedPaxosStoreService detailedPaxosStoreService;
    private final DetailedPaxosStoreRepository detailedPaxosStoreRepository;

    public PaxosStoreService(PaxosStoreRepository paxosStoreRepository, DetailedPaxosStoreService detailedPaxosStoreService, DetailedPaxosStoreRepository detailedPaxosStoreRepository) {
        this.paxosStoreRepository = paxosStoreRepository;
        this.detailedPaxosStoreService = detailedPaxosStoreService;
        this.detailedPaxosStoreRepository = detailedPaxosStoreRepository;
    }

    public void createInitialPaxosStore(TempData tempData){
        PaxosStore paxosStore = PaxosStore
                .builder()
                .tempData(tempData)
                .state(State.PROPOSER_REQUESTED)
                .status(Status.IN_PROCESS)
                .createdOn(Instant.now())
                .updatedOn(Instant.now())
                .build();
        paxosStore = paxosStoreRepository.save(paxosStore);
        detailedPaxosStoreService.createInitialDetailedPaxosStore(paxosStore);
    }

    public void updatedPaxosStoreStatus(PaxosStore paxosStore){
        List<DetailedPaxosStore> detailedPaxosStores = IteratorUtils.toList((detailedPaxosStoreRepository.findAllByPaxosStore_Id(paxosStore.getId())));
        PaxosStore latestPaxosStore = paxosStoreRepository.findById(paxosStore.getId()).get();
        if(latestPaxosStore.getState().equals(State.PROPOSER_REQUESTED)){
            long totalProposerResponded = detailedPaxosStores.stream().filter(d-> d.getState().equals(State.PROPOSER_RESPONDED)).count();
            long totalServers = detailedPaxosStores.stream().count();
            if(totalServers==totalProposerResponded){
                latestPaxosStore.setState(State.ACCEPTOR_REQUESTED);
                // todo create acceptor service
            }
        }
    }
}

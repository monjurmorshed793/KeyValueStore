package bd.ac.buet.KeyValueStore.service;

import bd.ac.buet.KeyValueStore.model.PaxosStore;
import bd.ac.buet.KeyValueStore.model.TempData;
import bd.ac.buet.KeyValueStore.model.enumeration.State;
import bd.ac.buet.KeyValueStore.model.enumeration.Status;
import bd.ac.buet.KeyValueStore.repository.DetailedPaxosStoreRepository;
import bd.ac.buet.KeyValueStore.repository.PaxosStoreRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PaxosStoreService {
    private final PaxosStoreRepository paxosStoreRepository;
    private final DetailedPaxosStoreService detailedPaxosStoreService;

    public PaxosStoreService(PaxosStoreRepository paxosStoreRepository, DetailedPaxosStoreService detailedPaxosStoreService) {
        this.paxosStoreRepository = paxosStoreRepository;
        this.detailedPaxosStoreService = detailedPaxosStoreService;
    }

    public void createInitialPaxosStore(TempData tempData){
        PaxosStore paxosStore = PaxosStore
                .builder()
                .tempData(tempData)
                .state(State.PROPOSER)
                .status(Status.IN_PROCESS)
                .createdOn(Instant.now())
                .updatedOn(Instant.now())
                .build();
        paxosStore = paxosStoreRepository.save(paxosStore);
        detailedPaxosStoreService.createInitialDetailedPaxosStore(paxosStore);
    }
}

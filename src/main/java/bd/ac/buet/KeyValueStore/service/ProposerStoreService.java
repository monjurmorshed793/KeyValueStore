package bd.ac.buet.KeyValueStore.service;

import bd.ac.buet.KeyValueStore.model.DetailedProposerStore;
import bd.ac.buet.KeyValueStore.model.ProposerStore;
import bd.ac.buet.KeyValueStore.model.TempData;
import bd.ac.buet.KeyValueStore.model.enumeration.State;
import bd.ac.buet.KeyValueStore.model.enumeration.Status;
import bd.ac.buet.KeyValueStore.repository.DetailedProposerStoreRepository;
import bd.ac.buet.KeyValueStore.repository.ProposerStoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
public class ProposerStoreService {
    private final ProposerStoreRepository proposerStoreRepository;
    private final DetailedProposerStoreService detailedProposerStoreService;
    private final DetailedProposerStoreRepository detailedProposerStoreRepository;
    private final KafkaProducer kafkaProducer;

    public ProposerStoreService(ProposerStoreRepository proposerStoreRepository, @Lazy DetailedProposerStoreService detailedProposerStoreService, DetailedProposerStoreRepository detailedProposerStoreRepository, KafkaProducer kafkaProducer) {
        this.proposerStoreRepository = proposerStoreRepository;
        this.detailedProposerStoreService = detailedProposerStoreService;
        this.detailedProposerStoreRepository = detailedProposerStoreRepository;
        this.kafkaProducer = kafkaProducer;
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
        log.info("Create initial proposer store");
        proposerStore = proposerStoreRepository.save(proposerStore);
        log.info("Create initial proposer store success");
        detailedProposerStoreService.createInitialDetailedProposerStore(proposerStore);
    }

    public void updatedProposerStoreStatus(ProposerStore proposerStore){
        List<DetailedProposerStore> detailedProposerStores = (detailedProposerStoreRepository.findByProposerStoreId(proposerStore.getId()));
        ProposerStore latestProposerStore = proposerStoreRepository.findById(proposerStore.getId()).get();
        if(latestProposerStore.getState().equals(State.PROPOSER_REQUESTED)){
            long totalProposerResponded = detailedProposerStores.stream().filter(d-> d.getState().equals(State.PROPOSER_RESPONDED)).count();
            long totalServers = detailedProposerStores.stream().count();
            if(detailedProposerStores.size()>0 && totalProposerResponded>= ((totalServers/2)+1) && (!latestProposerStore.getStatus().equals(Status.REJECTED) || !latestProposerStore.getStatus().equals(Status.ACCEPTED))){
                TempData latestTempData = detailedProposerStores
                        .stream()
                        .map(d-> d.getRespondedTempData())
                        .sorted((o1,o2)-> o1.getUpdatedOn().compareTo(o2.getUpdatedOn()))
                        .findFirst().get();
                if(latestTempData.getId().equals(proposerStore.getTempData().getId())){
                    latestProposerStore.setState(State.ACCEPTOR_REQUESTED);
                    latestProposerStore.setStatus(Status.ACCEPTED);
                    kafkaProducer.send("acceptor-request", proposerStore.getTempData());
                }else{
                    latestProposerStore.setState(State.PROPOSER_RESPONDED);
                    latestProposerStore.setStatus(Status.REJECTED);
                    TempData tempData = latestProposerStore.getTempData();
                    tempData.setStatus(Status.REJECTED);
                }
                latestProposerStore.setUpdatedOn(Instant.now());
                proposerStoreRepository.save(latestProposerStore);
            }
        }
    }
}

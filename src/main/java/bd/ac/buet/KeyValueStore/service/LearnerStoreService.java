package bd.ac.buet.KeyValueStore.service;

import bd.ac.buet.KeyValueStore.model.DetailedLearnerStore;
import bd.ac.buet.KeyValueStore.model.LearnerStore;
import bd.ac.buet.KeyValueStore.model.ServerInfo;
import bd.ac.buet.KeyValueStore.model.TempData;
import bd.ac.buet.KeyValueStore.model.enumeration.State;
import bd.ac.buet.KeyValueStore.model.enumeration.Status;
import bd.ac.buet.KeyValueStore.repository.DetailedLearnerStoreRepository;
import bd.ac.buet.KeyValueStore.repository.LearnerStoreRepository;
import bd.ac.buet.KeyValueStore.repository.ObjectStoreRepository;
import bd.ac.buet.KeyValueStore.repository.ServerInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
public class LearnerStoreService {
    private final LearnerStoreRepository learnerStoreRepository;
    private final DetailedLearnerStoreRepository detailedLearnerStoreRepository;
    private final ObjectStoreRepository objectStoreRepository;
    private final ServerInfoRepository serverInfoRepository;

    public LearnerStoreService(LearnerStoreRepository learnerStoreRepository, DetailedLearnerStoreRepository detailedLearnerStoreRepository, ObjectStoreRepository objectStoreRepository, ServerInfoRepository serverInfoRepository) {
        this.learnerStoreRepository = learnerStoreRepository;
        this.detailedLearnerStoreRepository = detailedLearnerStoreRepository;
        this.objectStoreRepository = objectStoreRepository;
        this.serverInfoRepository = serverInfoRepository;
    }

    public void createLearnerStore(TempData tempData){
        LearnerStore learnerStore =
                LearnerStore
                .builder()
                .tempData(tempData)
                .state(State.LEARNER_REQUESTED)
                .status(Status.IN_PROCESS)
                .createdOn(Instant.now())
                .updatedOn(Instant.now())
                .build();
        learnerStoreRepository.save(learnerStore);
    }

    public LearnerStore updateLearnerStore(LearnerStore learnerStore){
        List<DetailedLearnerStore> detailedLearnerStoreList = IteratorUtils.toList(detailedLearnerStoreRepository.findAllByLearnerStore_Id(learnerStore.getId()));
        List<ServerInfo> serverInfoList = IteratorUtils.toList(serverInfoRepository.findAll().iterator());
        if(detailedLearnerStoreList.size() == serverInfoList.size()){
            learnerStore.setState(State.LEARNER_RESPONDED);
            learnerStore.setStatus(Status.ACCEPTED);
            return learnerStoreRepository.save(learnerStore);
        }else
            return learnerStore;
    }
}

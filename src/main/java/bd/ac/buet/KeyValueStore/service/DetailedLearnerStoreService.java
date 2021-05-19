package bd.ac.buet.KeyValueStore.service;

import bd.ac.buet.KeyValueStore.dto.LearnerRequestDto;
import bd.ac.buet.KeyValueStore.model.DetailedLearnerStore;
import bd.ac.buet.KeyValueStore.model.LearnerStore;
import bd.ac.buet.KeyValueStore.model.enumeration.State;
import bd.ac.buet.KeyValueStore.model.enumeration.Status;
import bd.ac.buet.KeyValueStore.repository.DetailedLearnerStoreRepository;
import bd.ac.buet.KeyValueStore.repository.LearnerStoreRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class DetailedLearnerStoreService {
    private final DetailedLearnerStoreRepository detailedLearnerStoreRepository;
    private final LearnerStoreRepository learnerStoreRepository;

    public DetailedLearnerStoreService(DetailedLearnerStoreRepository detailedLearnerStoreRepository, LearnerStoreRepository learnerStoreRepository) {
        this.detailedLearnerStoreRepository = detailedLearnerStoreRepository;
        this.learnerStoreRepository = learnerStoreRepository;
    }

    public DetailedLearnerStore create(LearnerRequestDto learnerRequestDto){
        LearnerStore learnerStore = learnerStoreRepository.findByTempData_Id(learnerRequestDto.getTempData().getId()).get();
        DetailedLearnerStore detailedLearnerStore = DetailedLearnerStore
                .builder()
                .learnerStore(learnerStore)
                .serverInfo(learnerRequestDto.getServerInfo())
                .state(State.LEARNER_RESPONDED)
                .status(Status.ACCEPTED)
                .createdOn(Instant.now())
                .updatedOn(Instant.now())
                .build();
        return detailedLearnerStoreRepository.save(detailedLearnerStore);
    }
}

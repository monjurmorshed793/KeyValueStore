package bd.ac.buet.KeyValueStore.service.paxos;

import bd.ac.buet.KeyValueStore.dto.LearnerRequestDto;
import bd.ac.buet.KeyValueStore.model.DetailedLearnerStore;
import bd.ac.buet.KeyValueStore.model.LearnerStore;
import bd.ac.buet.KeyValueStore.model.TempData;
import bd.ac.buet.KeyValueStore.model.enumeration.Status;
import bd.ac.buet.KeyValueStore.repository.DetailedLearnerStoreRepository;
import bd.ac.buet.KeyValueStore.repository.LearnerStoreRepository;
import bd.ac.buet.KeyValueStore.repository.TempDataRepository;
import bd.ac.buet.KeyValueStore.service.DetailedLearnerStoreService;
import bd.ac.buet.KeyValueStore.service.LearnerStoreService;
import bd.ac.buet.KeyValueStore.service.ObjectStoreService;
import org.springframework.stereotype.Service;

@Service
public class LearnerService {
    private final DetailedLearnerStoreRepository detailedLearnerStoreRepository;
    private final LearnerStoreRepository learnerStoreRepository;
    private final DetailedLearnerStoreService detailedLearnerStoreService;
    private final LearnerStoreService learnerStoreService;
    private final ObjectStoreService objectStoreService;
    private final TempDataRepository tempDataRepository;

    public LearnerService(DetailedLearnerStoreRepository detailedLearnerStoreRepository, LearnerStoreRepository learnerStoreRepository, DetailedLearnerStoreService detailedLearnerStoreService, LearnerStoreService learnerStoreService, ObjectStoreService objectStoreService, TempDataRepository tempDataRepository) {
        this.detailedLearnerStoreRepository = detailedLearnerStoreRepository;
        this.learnerStoreRepository = learnerStoreRepository;
        this.detailedLearnerStoreService = detailedLearnerStoreService;
        this.learnerStoreService = learnerStoreService;
        this.objectStoreService = objectStoreService;
        this.tempDataRepository = tempDataRepository;
    }

    public void processLearnerRequest(LearnerRequestDto learnerRequestDto){
        if (!detailedLearnerStoreRepository.existsByServerInfoIdAndLearnerStoreTempDataId(learnerRequestDto.getServerInfo().getId(), learnerRequestDto.getTempData().getId())) {
            DetailedLearnerStore detailedLearnerStore = detailedLearnerStoreService.create(learnerRequestDto);
            LearnerStore learnerStore =  learnerStoreService.updateLearnerStore(detailedLearnerStore.getLearnerStore());
            if(learnerStore.getState().equals(Status.ACCEPTED)){
                objectStoreService.createOrUpdateObjectStore(learnerStore.getTempData());
                TempData tempData = learnerStore.getTempData();
                tempData.setStatus(Status.ACCEPTED);
                tempDataRepository.save(tempData);
            }
        }
    }
}


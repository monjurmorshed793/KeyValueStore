package bd.ac.buet.KeyValueStore.service.paxos;

import bd.ac.buet.KeyValueStore.dto.LearnerRequestDto;
import bd.ac.buet.KeyValueStore.model.DetailedLearnerStore;
import bd.ac.buet.KeyValueStore.model.LearnerStore;
import bd.ac.buet.KeyValueStore.model.TempData;
import bd.ac.buet.KeyValueStore.model.enumeration.Status;
import bd.ac.buet.KeyValueStore.model.enumeration.StoreType;
import bd.ac.buet.KeyValueStore.repository.DetailedLearnerStoreRepository;
import bd.ac.buet.KeyValueStore.repository.LearnerStoreRepository;
import bd.ac.buet.KeyValueStore.repository.TempDataRepository;
import bd.ac.buet.KeyValueStore.service.DetailedLearnerStoreService;
import bd.ac.buet.KeyValueStore.service.LearnerStoreService;
import bd.ac.buet.KeyValueStore.service.ObjectStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
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
        if (!detailedLearnerStoreRepository.findByServerInfoIdAndLearnerStoreTempDataId(learnerRequestDto.getServerInfo().getId(), learnerRequestDto.getTempData().getId()).isPresent()) {
            DetailedLearnerStore detailedLearnerStore = detailedLearnerStoreService.create(learnerRequestDto);
            LearnerStore learnerStore =  learnerStoreService.updateLearnerStore(detailedLearnerStore.getLearnerStore());
            log.info(learnerStore.getStatus().name());
            if(learnerStore.getStatus().equals(Status.ACCEPTED)){
                if(learnerStore.getTempData().getStoreType().equals(StoreType.DELETE)){
                    objectStoreService.deleteObjectStore(learnerStore.getTempData().getObjectId());
                }else{
                    objectStoreService.createOrUpdateObjectStore(learnerStore.getTempData());
                }
                TempData tempData = learnerStore.getTempData();
                tempData.setStatus(Status.ACCEPTED);
                tempDataRepository.save(tempData);
            }
        }
    }
}


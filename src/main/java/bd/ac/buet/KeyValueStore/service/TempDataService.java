package bd.ac.buet.KeyValueStore.service;

import bd.ac.buet.KeyValueStore.model.ObjectStore;
import bd.ac.buet.KeyValueStore.model.TempData;
import bd.ac.buet.KeyValueStore.repository.TempDataRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TempDataService {
    private final TempDataRepository tempDataRepository;

    public TempDataService(TempDataRepository tempDataRepository) {
        this.tempDataRepository = tempDataRepository;
    }

    public ObjectStore convertToObjectStore(TempData tempData){
        ObjectStore objectStore = ObjectStore
                .builder()
                .id(tempData.getId())
                .customObject(tempData.getObject())
                .createdOn(tempData.getCreatedOn())
                .updatedOn(tempData.getUpdatedOn())
                .build();
        return objectStore;
    }

    public TempData save(TempData tempData){
        tempData.setCreatedOn(tempData.getCreatedOn()==null? Instant.now(): tempData.getCreatedOn());
        tempData.setUpdatedOn(Instant.now());
        return tempDataRepository.save(tempData);
    }
}

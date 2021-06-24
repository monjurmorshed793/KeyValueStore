package bd.ac.buet.KeyValueStore.service;

import bd.ac.buet.KeyValueStore.model.ApplicationInfo;
import bd.ac.buet.KeyValueStore.model.ObjectStore;
import bd.ac.buet.KeyValueStore.model.ServerInfo;
import bd.ac.buet.KeyValueStore.model.TempData;
import bd.ac.buet.KeyValueStore.model.enumeration.Status;
import bd.ac.buet.KeyValueStore.repository.ApplicationInfoRepository;
import bd.ac.buet.KeyValueStore.repository.ObjectStoreRepository;
import bd.ac.buet.KeyValueStore.repository.ServerInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
public class ObjectStoreService {
    private final ApplicationInfoRepository applicationInfoRepository;
    private final ServerInfoRepository serverInfoRepository;
    private final ObjectStoreRepository objectStoreRepository;

    public ObjectStoreService(ApplicationInfoRepository applicationInfoRepository, ServerInfoRepository serverInfoRepository, ObjectStoreRepository objectStoreRepository) {
        this.applicationInfoRepository = applicationInfoRepository;
        this.serverInfoRepository = serverInfoRepository;
        this.objectStoreRepository = objectStoreRepository;
    }

    public TempData convertToTempData(ObjectStore objectStore){
        ApplicationInfo applicationInfo = applicationInfoRepository.findAll().iterator().next();
        log.info("----------applicaton name ------------");
        log.info(applicationInfo.getName());
        ServerInfo parentServerInfo = serverInfoRepository.findByNameEquals(applicationInfo.getName()).get();
        TempData tempData = TempData
                .builder()
                .objectId(objectStore.getId())
                .object(objectStore.getCustomObject())
                .proposedBy(parentServerInfo)
                .status(Status.IN_PROCESS)
                .createdOn(objectStore.getCreatedOn()==null? Instant.now(): objectStore.getCreatedOn())
                .updatedOn(Instant.now())
                .build();
        return tempData;
    }

    public ObjectStore createOrUpdateObjectStore(TempData tempData){
        if(tempData.getObjectId()!=null && objectStoreRepository.existsById(tempData.getObjectId())){
            ObjectStore objectStore = ObjectStore
                    .builder()
                    .id(tempData.getObjectId())
                    .customObject(tempData.getObject())
                    .updatedOn(Instant.now())
                    .build();
            return objectStoreRepository.save(objectStore);
        }else{
            ObjectStore objectStore = ObjectStore
                    .builder()
                    .id(tempData.getId())
                    .customObject(tempData.getObject())
                    .updatedOn(Instant.now())
                    .build();
            return objectStoreRepository.save(objectStore);
        }
    }

    public void deleteObjectStore(String id){
        objectStoreRepository.deleteById(id);
    }

    public void restoreObjectStores(List<ObjectStore> objectStoreList){
        objectStoreRepository.saveAll(objectStoreList);
    }

    public void deleteObjectStores(List<String> objectStoreIds){
        for(String id: objectStoreIds){
            if(objectStoreRepository.existsById(id)){
                objectStoreRepository.deleteById(id);
            }
        }
    }
}

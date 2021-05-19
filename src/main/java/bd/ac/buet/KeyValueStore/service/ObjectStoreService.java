package bd.ac.buet.KeyValueStore.service;

import bd.ac.buet.KeyValueStore.model.ApplicationInfo;
import bd.ac.buet.KeyValueStore.model.ObjectStore;
import bd.ac.buet.KeyValueStore.model.ServerInfo;
import bd.ac.buet.KeyValueStore.model.TempData;
import bd.ac.buet.KeyValueStore.model.enumeration.Status;
import bd.ac.buet.KeyValueStore.repository.ApplicationInfoRepository;
import bd.ac.buet.KeyValueStore.repository.ServerInfoRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ObjectStoreService {
    private final ApplicationInfoRepository applicationInfoRepository;
    private final ServerInfoRepository serverInfoRepository;

    public ObjectStoreService(ApplicationInfoRepository applicationInfoRepository, ServerInfoRepository serverInfoRepository) {
        this.applicationInfoRepository = applicationInfoRepository;
        this.serverInfoRepository = serverInfoRepository;
    }

    public TempData convertToTempData(ObjectStore objectStore){
        ApplicationInfo applicationInfo = applicationInfoRepository.findAll().iterator().next();
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
}

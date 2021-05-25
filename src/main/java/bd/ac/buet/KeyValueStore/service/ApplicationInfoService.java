package bd.ac.buet.KeyValueStore.service;

import bd.ac.buet.KeyValueStore.model.ApplicationInfo;
import bd.ac.buet.KeyValueStore.model.ServerInfo;
import org.springframework.stereotype.Service;

@Service
public class ApplicationInfoService {
    public ServerInfo convert(ApplicationInfo applicationInfo){
        return ServerInfo
                .builder()
                .id(applicationInfo.getId())
                .name(applicationInfo.getName())
                .createdOn(applicationInfo.getCreatedOn())
                .updatedOn(applicationInfo.getUpdatedOn())
                .build();
    }
}

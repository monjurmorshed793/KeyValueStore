package bd.ac.buet.KeyValueStore.service;

import bd.ac.buet.KeyValueStore.model.ApplicationInfo;
import bd.ac.buet.KeyValueStore.model.ServerInfo;
import bd.ac.buet.KeyValueStore.repository.ApplicationInfoRepository;
import bd.ac.buet.KeyValueStore.repository.ServerInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@Slf4j
public class ServerInfoService {
    private final ServerInfoRepository serverInfoRepository;
    private final ApplicationInfoRepository applicationInfoRepository;

    public ServerInfoService(ServerInfoRepository serverInfoRepository, ApplicationInfoRepository applicationInfoRepository) {
        this.serverInfoRepository = serverInfoRepository;
        this.applicationInfoRepository = applicationInfoRepository;
    }

    public ServerInfo storeServerInfo(ServerInfo serverInfo){
        Optional<ServerInfo> existingServerInfo = serverInfoRepository.findByNameEquals(serverInfo.getName());
        if(existingServerInfo.isPresent())
            serverInfoRepository.deleteById(existingServerInfo.get().getId());
        return serverInfoRepository.save(serverInfo);
    }

    public ServerInfo getSelfServerInfo(){
        ApplicationInfo selfApplicationInfo = applicationInfoRepository.findAll().iterator().next();
        return serverInfoRepository.findByNameEquals(selfApplicationInfo.getName()).get();
    }
}

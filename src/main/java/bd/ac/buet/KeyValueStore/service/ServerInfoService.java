package bd.ac.buet.KeyValueStore.service;

import bd.ac.buet.KeyValueStore.model.ApplicationInfo;
import bd.ac.buet.KeyValueStore.model.ServerInfo;
import bd.ac.buet.KeyValueStore.model.enumeration.ServerStatus;
import bd.ac.buet.KeyValueStore.repository.ApplicationInfoRepository;
import bd.ac.buet.KeyValueStore.repository.ServerInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;
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
        if(existingServerInfo.isPresent()){
            existingServerInfo.get().setUpdatedOn(Instant.now());
            serverInfo = existingServerInfo.get();
            serverInfo.setServerStatus(ServerStatus.UP);
        }
        return serverInfoRepository.save(serverInfo);
    }

    public ServerInfo getSelfServerInfo(){
        ApplicationInfo selfApplicationInfo = applicationInfoRepository.findAll().iterator().next();
        return serverInfoRepository.findByNameEquals(selfApplicationInfo.getName()).get();
    }

    @Scheduled(fixedRate = 10000) // broadcasting at 10s intervals
    public void serverStatusChecker(){
        Iterator<ServerInfo> allServers = serverInfoRepository.findAll().iterator();
        ServerInfo selfServerInfo = getSelfServerInfo();
        while(allServers.hasNext()){
            ServerInfo serverInfo = allServers.next();
            if(!serverInfo.getId().equals(selfServerInfo.getId())){  // skip self server
                Duration duration = Duration.between(Instant.now(), serverInfo.getUpdatedOn());
                long diffInSeconds = duration.getSeconds();
                if(diffInSeconds>12){     // if longer than 12 seconds, then mark as DOWN
                    serverInfo.setServerStatus(ServerStatus.DOWN);
                    serverInfoRepository.save(serverInfo);
                }
            }
        }
    }
}

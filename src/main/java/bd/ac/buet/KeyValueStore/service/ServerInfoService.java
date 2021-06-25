package bd.ac.buet.KeyValueStore.service;

import bd.ac.buet.KeyValueStore.dto.RestoreDTO;
import bd.ac.buet.KeyValueStore.model.ApplicationInfo;
import bd.ac.buet.KeyValueStore.model.ObjectStore;
import bd.ac.buet.KeyValueStore.model.ServerInfo;
import bd.ac.buet.KeyValueStore.model.enumeration.ServerStatus;
import bd.ac.buet.KeyValueStore.model.enumeration.StoreType;
import bd.ac.buet.KeyValueStore.repository.ApplicationInfoRepository;
import bd.ac.buet.KeyValueStore.repository.ObjectStoreRepository;
import bd.ac.buet.KeyValueStore.repository.ServerInfoRepository;
import bd.ac.buet.KeyValueStore.repository.TempDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
public class ServerInfoService {
    private final ServerInfoRepository serverInfoRepository;
    private final ApplicationInfoRepository applicationInfoRepository;
    private final ObjectStoreRepository objectStoreRepository;
    private final TempDataRepository tempDataRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ServerInfoService(ServerInfoRepository serverInfoRepository, ApplicationInfoRepository applicationInfoRepository, @Lazy ObjectStoreRepository objectStoreRepository, @Lazy TempDataRepository tempDataRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.serverInfoRepository = serverInfoRepository;
        this.applicationInfoRepository = applicationInfoRepository;
        this.objectStoreRepository = objectStoreRepository;
        this.tempDataRepository = tempDataRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public ServerInfo storeServerInfo(ServerInfo serverInfo){
        Optional<ServerInfo> existingServerInfo = serverInfoRepository.findByNameEquals(serverInfo.getName());
        if(existingServerInfo.isPresent()){
            if(existingServerInfo.get().getServerStatus().equals(ServerStatus.DOWN)){
                sendUpdatedDataToDownServer(existingServerInfo.get());
            }
            existingServerInfo.get().setUpdatedOn(Instant.now());
            serverInfo = existingServerInfo.get();
            serverInfo.setServerStatus(ServerStatus.UP);
        }
        return serverInfoRepository.save(serverInfo);
    }

    public void sendUpdatedDataToDownServer(ServerInfo serverInfo){
        List<ObjectStore> objectStores = objectStoreRepository.findAllByUpdatedOnAfter(serverInfo.getUpdatedOn());
        List<String> deleteIds = tempDataRepository.findAllByStoreTypeAndUpdatedOnAfter(StoreType.DELETE, serverInfo.getUpdatedOn())
                .stream()
                .map(t-> t.getObjectId())
                .collect(Collectors.toList());
        RestoreDTO restoreDTO = RestoreDTO
                .builder()
                .serverInfo(serverInfo)
                .objectStores(objectStores)
                .deleteIds(deleteIds)
                .build();
        kafkaTemplate.send("restore", restoreDTO);
    }

    public ServerInfo getSelfServerInfo(){
        ApplicationInfo selfApplicationInfo = applicationInfoRepository.findAll().iterator().next();
        return serverInfoRepository.findByNameEquals(selfApplicationInfo.getName()).get();
    }

/*    @Scheduled(fixedRate = 10000) // broadcasting at 10s intervals
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
    }*/
}

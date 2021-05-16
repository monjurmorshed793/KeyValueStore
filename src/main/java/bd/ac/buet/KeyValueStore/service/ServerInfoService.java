package bd.ac.buet.KeyValueStore.service;

import bd.ac.buet.KeyValueStore.model.ServerInfo;
import bd.ac.buet.KeyValueStore.repository.ServerInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@Slf4j
public class ServerInfoService {
    private final ServerInfoRepository serverInfoRepository;

    public ServerInfoService(ServerInfoRepository serverInfoRepository) {
        this.serverInfoRepository = serverInfoRepository;
    }

    public ServerInfo storeServerInfo(ServerInfo serverInfo){
        Iterable<ServerInfo> existingServerInfos = serverInfoRepository.findAll();
        existingServerInfos.forEach(s->{
            if(s.getName().equals(serverInfo.getName())){
                this.serverInfoRepository.deleteById(s.getId());
                return; // used to work like break as break statement doesn't work inside lamda foreach.
            }
        });
        return serverInfoRepository.save(serverInfo);
    }
}

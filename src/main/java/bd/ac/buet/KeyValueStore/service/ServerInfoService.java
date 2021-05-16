package bd.ac.buet.KeyValueStore.service;

import bd.ac.buet.KeyValueStore.model.ServerInfo;
import bd.ac.buet.KeyValueStore.repository.ServerInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@Slf4j
public class ServerInfoService {
    private final ServerInfoRepository serverInfoRepository;

    public ServerInfoService(ServerInfoRepository serverInfoRepository) {
        this.serverInfoRepository = serverInfoRepository;
    }

    public ServerInfo storeServerInfo(ServerInfo serverInfo){
        Optional<ServerInfo> existingServerInfo = serverInfoRepository.findByNameEquals(serverInfo.getName());
        if(existingServerInfo.isPresent())
            serverInfoRepository.deleteById(existingServerInfo.get().getId());
        return serverInfoRepository.save(serverInfo);
    }
}

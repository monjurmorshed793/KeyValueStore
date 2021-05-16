package bd.ac.buet.KeyValueStore.service;

import bd.ac.buet.KeyValueStore.configuration.TestRedisConfiguration;
import bd.ac.buet.KeyValueStore.model.ServerInfo;
import bd.ac.buet.KeyValueStore.repository.ServerInfoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


import java.time.Instant;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestRedisConfiguration.class)
@ExtendWith(SpringExtension.class)
public class ServerInfoServiceTest {
    @Autowired
    private ServerInfoRepository serverInfoRepository;
    @Autowired
    private ServerInfoService serverInfoService;

    ServerInfo initialServerInfo;

    private void storeInitialServerInfo(){
        initialServerInfo = ServerInfo
                .builder()
                .name("server1")
                .updatedOn(Instant.now())
                .createdOn(Instant.now())
                .build();
        initialServerInfo = serverInfoRepository.save(initialServerInfo);
    }

    @Test
    public void storeServerInfoTest() throws Exception {
        storeInitialServerInfo();
        ServerInfo newServerInfo = ServerInfo
                .builder()
                .name("server1")
                .updatedOn(Instant.now())
                .updatedOn(Instant.now())
                .build();
         newServerInfo = serverInfoService.storeServerInfo(newServerInfo);

        Optional<ServerInfo> updatedStoredServerInfo = serverInfoRepository.findByNameEquals(newServerInfo.getName());
        if(updatedStoredServerInfo.isEmpty())
            throw new Exception("No data found");
        assertEquals(updatedStoredServerInfo.get().getId(), newServerInfo.getId());
    }
}

package bd.ac.buet.KeyValueStore.repository;

import bd.ac.buet.KeyValueStore.configuration.TestRedisConfiguration;
import bd.ac.buet.KeyValueStore.model.ServerInfo;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.shaded.org.apache.commons.lang.math.RandomUtils;

import java.time.Instant;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestRedisConfiguration.class)
@ExtendWith(SpringExtension.class)
public class ServerInfoRepositoryTest {
    @Autowired
    private ServerInfoRepository serverInfoRepository;

    @Test
    public void shouldSaveServerInfo(){
        serverInfoRepository.deleteAll();
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setName("server1");
        serverInfo.setUpdatedOn(Instant.now());
        serverInfo = serverInfoRepository.save(serverInfo);
        assertNotNull(serverInfo);
    }

    @Test
    public void shouldRetrieveServerInfo(){
        serverInfoRepository.deleteAll();
        ServerInfo serverInfo = ServerInfo
                .builder()
                .name("server1")
                .updatedOn(Instant.now()).build();
        serverInfo = serverInfoRepository.save(serverInfo);

        ServerInfo serverInfoAfterSave = serverInfoRepository.findById(serverInfo.getId()).get();
        assertEquals(serverInfoAfterSave.getId(), serverInfo.getId());
        assertEquals(serverInfoAfterSave.getName(), serverInfo.getName());
    }

    public static ServerInfo createServerInfo(){
       return ServerInfo
                .builder()
                .name("server1")
                .updatedOn(Instant.now()).build();
    }
}

package bd.ac.buet.KeyValueStore.repository;

import bd.ac.buet.KeyValueStore.configuration.TestRedisConfiguration;
import bd.ac.buet.KeyValueStore.model.TempData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestRedisConfiguration.class)
@ExtendWith(SpringExtension.class)
public class TempDataRepositoryTest {
    @Autowired
    private TempDataRepository tempDataRepository;
    @Autowired
    private ProposerStoreRepository proposerStoreRepository;
    @Autowired
    private ServerInfoRepository serverInfoRepository;
    @Autowired
    private DetailedProposerStoreRepository detailedProposerStoreRepository;

    @Test
    public void testContext(){

    }

    @Test
    public void testConnectedObjects(){
        TempData tempData = TempData
                .builder()
                .id("1")
                .objectId("1")
                .object("hello")
                .updatedOn(Instant.now())
                .createdOn(Instant.now())
                .build();

        tempDataRepository.save(tempData);

    }
}

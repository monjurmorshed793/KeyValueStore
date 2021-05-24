package bd.ac.buet.KeyValueStore.repository;

import bd.ac.buet.KeyValueStore.configuration.TestRedisConfiguration;
import bd.ac.buet.KeyValueStore.model.TempData;
import bd.ac.buet.KeyValueStore.model.enumeration.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.List;

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
                .status(Status.ACCEPTED)
                .updatedOn(Instant.now())
                .createdOn(Instant.now())
                .build();
        tempDataRepository.save(tempData);

        tempData = TempData
                .builder()
                .id("2")
                .objectId("1")
                .object("hello2")
                .status(Status.ACCEPTED)
                .updatedOn(Instant.now())
                .createdOn(Instant.now())
                .build();
        tempDataRepository.save(tempData);

        List<TempData> tempDataList = tempDataRepository.findByObjectIdAndStatusOrderByCreatedOnDesc("1", Status.ACCEPTED);

        System.out.println(tempDataList.size());

    }
}

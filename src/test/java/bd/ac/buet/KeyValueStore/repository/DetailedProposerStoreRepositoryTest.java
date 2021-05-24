package bd.ac.buet.KeyValueStore.repository;

import bd.ac.buet.KeyValueStore.configuration.TestRedisConfiguration;
import bd.ac.buet.KeyValueStore.model.DetailedProposerStore;
import bd.ac.buet.KeyValueStore.model.ProposerStore;
import bd.ac.buet.KeyValueStore.model.ServerInfo;
import bd.ac.buet.KeyValueStore.model.TempData;
import bd.ac.buet.KeyValueStore.model.enumeration.State;
import bd.ac.buet.KeyValueStore.model.enumeration.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Iterator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestRedisConfiguration.class)
@ExtendWith(SpringExtension.class)
public class DetailedProposerStoreRepositoryTest {
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
                .object("hello")
                .updatedOn(Instant.now())
                .createdOn(Instant.now())
                .build();
        tempData = tempDataRepository.save(tempData);
        ProposerStore proposerStore = ProposerStore
                .builder()
                .id("2")
                .state(State.PROPOSER_REQUESTED)
                .status(Status.IN_PROCESS)
                .tempData(tempData)
                .createdOn(Instant.now())
                .updatedOn(Instant.now())
                .build();
        proposerStore = proposerStoreRepository.save(proposerStore);

        ServerInfo serverInfo = ServerInfo
                .builder()
                .name("server1")
                .id("3")
                .createdOn(Instant.now())
                .updatedOn(Instant.now())
                .build();
        serverInfo = serverInfoRepository.save(serverInfo);

        DetailedProposerStore detailedProposerStore = DetailedProposerStore
                .builder()
                .proposerStore(proposerStore)
                .serverInfo(serverInfo)
                .id("4")
                .state(State.PROPOSER_RESPONDED)
                .status(Status.ACCEPTED)
                .build();
        detailedProposerStore = detailedProposerStoreRepository.save(detailedProposerStore);

        DetailedProposerStore fetchDetailedProposerStore = detailedProposerStoreRepository.findById(detailedProposerStore.getId()).get();

        Iterator<DetailedProposerStore> detailedProposerStoreIterator = detailedProposerStoreRepository.findAllByProposerStoreId(proposerStore.getId());

    }
}

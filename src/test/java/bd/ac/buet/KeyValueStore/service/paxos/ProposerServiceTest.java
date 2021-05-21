package bd.ac.buet.KeyValueStore.service.paxos;

import bd.ac.buet.KeyValueStore.configuration.TestRedisConfiguration;
import bd.ac.buet.KeyValueStore.helper.DummyObjectStore;
import bd.ac.buet.KeyValueStore.model.ObjectStore;
import bd.ac.buet.KeyValueStore.model.TempData;
import bd.ac.buet.KeyValueStore.repository.TempDataRepository;
import bd.ac.buet.KeyValueStore.service.ObjectStoreService;
import bd.ac.buet.KeyValueStore.service.TempDataService;
import org.apache.commons.collections4.IteratorUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestRedisConfiguration.class)
@ExtendWith(SpringExtension.class)
public class ProposerServiceTest {
    @Autowired
    private ProposerService proposerService;
    @Autowired
    private ObjectStoreService objectStoreService;
    @Autowired
    private TempDataService tempDataService;
    @Autowired
    private TempDataRepository tempDataRepository;

    @Test
    public void contextTest(){
        assertThat(proposerService).isNotNull();
        assertThat(objectStoreService).isNotNull();
        assertThat(tempDataService).isNotNull();
        assertThat(tempDataRepository).isNotNull();
    }


}

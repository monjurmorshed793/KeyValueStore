package bd.ac.buet.KeyValueStore.controller;

import bd.ac.buet.KeyValueStore.configuration.TestRedisConfiguration;
import bd.ac.buet.KeyValueStore.helper.DummyObjectStore;
import bd.ac.buet.KeyValueStore.model.ObjectStore;
import bd.ac.buet.KeyValueStore.repository.ObjectStoreRepository;
import bd.ac.buet.KeyValueStore.service.paxos.ProposerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestRedisConfiguration.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext
public class DataStorageControllerTest {
    @Autowired
    private DataStorageController dataStorageController;
    @InjectMocks
    private ProposerService proposerService;
    @Mock
    private ObjectStoreRepository objectStoreRepository;

    @Test
    public void contextLoads() throws Exception{
        assertThat(dataStorageController).isNotNull();
        assertThat(proposerService).isNotNull();
        assertThat(objectStoreRepository).isNotNull();
    }

    @Test
    public void storeOrUpdateDataTest(){
        ObjectStore objectStoreWithoutId = DummyObjectStore.createDummyObjectStore();
        ObjectStore objectStoreWithId = objectStoreWithoutId;
        objectStoreWithId.setId("1");

    }
}

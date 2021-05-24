package bd.ac.buet.KeyValueStore.controller;

import bd.ac.buet.KeyValueStore.configuration.TestRedisConfiguration;
import bd.ac.buet.KeyValueStore.helper.DummyObjectStore;
import bd.ac.buet.KeyValueStore.model.ObjectStore;
import bd.ac.buet.KeyValueStore.repository.ObjectStoreRepository;
import bd.ac.buet.KeyValueStore.service.paxos.ProposerService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestRedisConfiguration.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@DirtiesContext
@AutoConfigureMockMvc
public class DataStorageControllerTest {
    @Autowired
    private DataStorageController dataStorageController;
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private ProposerService proposerService;
    @Mock
    private ObjectStoreRepository objectStoreRepository;
    @LocalServerPort
    private Integer port;


    @Test
    public void contextLoads() throws Exception{
        assertThat(dataStorageController).isNotNull();
        assertThat(proposerService).isNotNull();
        assertThat(objectStoreRepository).isNotNull();
        assertThat(mockMvc).isNotNull();
        assertThat(port).isNotNull();
    }


    // todo does not work, it's for integration testing.
    @Test
    public void storeOrUpdateDataTest() throws Exception {
        ObjectStore objectStoreWithoutId = DummyObjectStore.createDummyObjectStore();
        ObjectStore objectStoreWithId = objectStoreWithoutId;
        objectStoreWithId.setId("1");
        Mockito.when(proposerService.propose(objectStoreWithoutId)).thenReturn(objectStoreWithId);
        assertThat(proposerService.propose(objectStoreWithoutId)).isEqualTo(objectStoreWithId);
        Gson gson = new Gson();

        this.mockMvc.perform(
                put("/")
                .content(gson.toJson(objectStoreWithoutId))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(objectStoreWithId.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customObject").value(objectStoreWithId.getCustomObject()));
    }
}

package bd.ac.buet.KeyValueStore.repository;

import bd.ac.buet.KeyValueStore.configuration.TestRedisConfiguration;
import bd.ac.buet.KeyValueStore.model.ObjectStore;
import bd.ac.buet.KeyValueStore.repository.helper.TestObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import net.minidev.json.reader.JsonWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestRedisConfiguration.class)
@ExtendWith(SpringExtension.class)
public class ObjectStoreRepositoryTest {
    @Autowired
    private ObjectStoreRepository objectStoreRepository;

    private TestObject createTestObject(){
        return TestObject
                .builder()
                .id("1")
                .name("test-object")
                .createdOn(Instant.now())
                .updatedOn(Instant.now())
                .build();
    }
    @Test
    public void testBasicCrudOperation(){
        TestObject testObject = createTestObject();
        Gson gson = new Gson();
        String testObjectJson = gson.toJson(testObject);
        ObjectStore objectStore = new ObjectStore();
        objectStore.setCustomObject(testObjectJson);

        objectStore = objectStoreRepository.save(objectStore);

        assertThat(objectStore.getId()).isNotNull();


        ObjectStore storedObjectStore = objectStoreRepository.findById(objectStore.getId()).get();
        TestObject storedTestObject = gson.fromJson(storedObjectStore.getCustomObject(), TestObject.class);
        assertThat(storedTestObject.getId()).isEqualTo(testObject.getId());
        assertThat(storedTestObject.getName()).isEqualTo(testObject.getName());
        assertThat(storedTestObject.getCreatedOn()).isEqualTo(testObject.getCreatedOn());
    }
}

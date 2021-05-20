package bd.ac.buet.KeyValueStore.controller;

import bd.ac.buet.KeyValueStore.configuration.TestRedisConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestRedisConfiguration.class)
@ExtendWith(SpringExtension.class)
public class DataStorageControllerTest {
    @Autowired
    private DataStorageController dataStorageController;

    @Test
    public void contextLoads() throws Exception{
        assertThat(dataStorageController).isNotNull();
    }
}

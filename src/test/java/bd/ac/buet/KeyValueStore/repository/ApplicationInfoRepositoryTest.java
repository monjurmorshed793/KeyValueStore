package bd.ac.buet.KeyValueStore.repository;

import bd.ac.buet.KeyValueStore.configuration.TestRedisConfiguration;
import bd.ac.buet.KeyValueStore.model.ApplicationInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestRedisConfiguration.class)
@ExtendWith(SpringExtension.class)
public class ApplicationInfoRepositoryTest {
    @Autowired
    private ApplicationInfoRepository applicationInfoRepository;

    @Test
    public void testSingleRecordRetrieval(){
        ApplicationInfo applicationInfo = applicationInfoRepository.findAll().iterator().next();
        assertNotNull(applicationInfo);
    }
}

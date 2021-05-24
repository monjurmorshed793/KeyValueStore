package bd.ac.buet.KeyValueStore.service.paxos;

import bd.ac.buet.KeyValueStore.configuration.TestRedisConfiguration;
import bd.ac.buet.KeyValueStore.model.LearnerStore;
import bd.ac.buet.KeyValueStore.model.enumeration.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestRedisConfiguration.class)
@ExtendWith(SpringExtension.class)
public class LearnerServiceTest {
    @Test
    public void testEnum(){
        Boolean status = false;

        LearnerStore learnerStore = LearnerStore
                .builder()
                .status(Status.ACCEPTED)
                .build();
        if(learnerStore.getStatus().equals(Status.ACCEPTED)){
            status=true;
        }

        assertThat(status).isTrue();
    }
}

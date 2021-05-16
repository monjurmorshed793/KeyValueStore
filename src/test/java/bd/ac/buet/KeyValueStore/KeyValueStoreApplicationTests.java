package bd.ac.buet.KeyValueStore;

import bd.ac.buet.KeyValueStore.configuration.TestRedisConfiguration;
import bd.ac.buet.KeyValueStore.model.ServerInfo;
import bd.ac.buet.KeyValueStore.service.KafkaConsumer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestRedisConfiguration.class)
@ExtendWith(SpringExtension.class)
class KeyValueStoreApplicationTests {
	@Autowired
	private KafkaConsumer consumer;

	@Value("${application.name}")
	private String applicationName;

	@Test
	void contextLoads() {
	}

	@Test
	void initialServerBroadcastTest() throws InterruptedException {
		consumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
		ServerInfo receivedServerInfo = consumer.getPayload();
		assertThat(receivedServerInfo.getName()).isEqualTo(applicationName);
	}

}

package bd.ac.buet.KeyValueStore.configuration;

import bd.ac.buet.KeyValueStore.service.KafkaConsumer;
import bd.ac.buet.KeyValueStore.model.ServerInfo;
import bd.ac.buet.KeyValueStore.repository.ServerInfoRepositoryTest;
import bd.ac.buet.KeyValueStore.service.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestRedisConfiguration.class)
@ExtendWith(SpringExtension.class)
public class ProducerAndConsumerTest {
    @Autowired
    private KafkaConsumer consumer;
    @Autowired
    private KafkaProducer producer;
    @Autowired
    private ApplicationProperties applicationProperties;

    @Test
    public void givenEmbeddedKafkaBroker_whenSendingToSimpleProducer_thenMessageReceived()
            throws Exception {
        ServerInfo providedServerInfo = ServerInfoRepositoryTest.createServerInfo();
        producer.send(applicationProperties.getApplicationTopic(), providedServerInfo);
        consumer.getLatch().await(10000, TimeUnit.MILLISECONDS);

        assertThat(consumer.getLatch().getCount()).isGreaterThanOrEqualTo(0L);

        ServerInfo receivedServerInfo = consumer.getPayload();
        assertThat(receivedServerInfo.getName()).isEqualTo(providedServerInfo.getName());
    }

}

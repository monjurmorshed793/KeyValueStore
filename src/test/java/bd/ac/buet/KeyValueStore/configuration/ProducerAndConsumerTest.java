package bd.ac.buet.KeyValueStore.configuration;

import bd.ac.buet.KeyValueStore.configuration.KafkaConsumer;
import bd.ac.buet.KeyValueStore.configuration.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@DirtiesContext
@EmbeddedKafka
public class ProducerAndConsumerTest {
    @Autowired
    private KafkaConsumer consumer;
    @Autowired
    private KafkaProducer producer;
    @Value("${application.topic}")
    private String topic;

    @Test
    public void givenEmbeddedKafkaBroker_whenSendingToSimpleProducer_thenMessageReceived()
            throws Exception {
        producer.send(topic, "Sending with own simple KafkaProducer");
        consumer.getLatch().await(10000, TimeUnit.MILLISECONDS);

        assertThat(consumer.getLatch().getCount()).isGreaterThanOrEqualTo(0L);
        assertThat(consumer.getPayload()).contains("paxos-topic");
        assertThat(consumer.getPayload()).contains("Sending with own simple KafkaProducer");
    }
}

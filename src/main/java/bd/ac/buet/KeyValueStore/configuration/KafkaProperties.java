package bd.ac.buet.KeyValueStore.configuration;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {

    private String bootStrapServers = "localhost:9092";

    private Map<String, String> consumer = new HashMap<>();

    private Map<String, String> producer = new HashMap<>();

    public String getBootStrapServers() {
        return bootStrapServers;
    }

    public void setBootStrapServers(String bootStrapServers) {
        this.bootStrapServers = bootStrapServers;
    }

    public Map<String, Object> getConsumerProps() {
        Map<String, Object> properties = new HashMap<>(this.consumer);
        if (!properties.containsKey("bootstrap.servers")) {
            properties.put("bootstrap.servers", this.bootStrapServers);
        }
        return properties;
    }

    public void setConsumer(Map<String, String> consumer) {
        this.consumer = consumer;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(getConsumerProps());
    }

    public Map<String, Object> getProducerProps() {
        Map<String, Object> properties = new HashMap<>(this.producer);
        if (!properties.containsKey("bootstrap.servers")) {
            properties.put("bootstrap.servers", this.bootStrapServers);
        }
        return properties;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory(){
        return new DefaultKafkaProducerFactory<>(getProducerProps());
    }


    public void setProducer(Map<String, String> producer) {
        this.producer = producer;
    }
}

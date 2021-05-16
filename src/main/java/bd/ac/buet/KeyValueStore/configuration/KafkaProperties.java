/*
package bd.ac.buet.KeyValueStore.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
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

    private Map<String, Object> consumer = new HashMap<>();

    private Map<String, Object> producer = new HashMap<>();

    private final ApplicationProperties applicationProperties;

    public KafkaProperties(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public String getBootStrapServers() {
        return bootStrapServers;
    }

    public void setBootStrapServers(String bootStrapServers) {
        this.bootStrapServers = bootStrapServers;
    }

    public Map<String, Object> getConsumerProps() {
        Map<String, Object> properties = new HashMap<>(this.consumer);
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, applicationProperties.getBootstrapServers());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, applicationProperties.getConsumerValueDeserializer());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, applicationProperties.getConsumerValueDeserializer());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, applicationProperties.getConsumerGroupId());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, applicationProperties.getConsumerAutoOffsetReset());
        return properties;
    }

    public void setConsumer(Map<String, Object> consumer) {
        this.consumer = consumer;
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(getConsumerProps());
    }

    public Map<String, Object> getProducerProps() {
        Map<String, Object> properties = new HashMap<>(this.producer);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, applicationProperties.getProducerKeySerializer());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, applicationProperties.getProducerValueSerializer());
        return properties;
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory(){
        return new DefaultKafkaProducerFactory<>(getProducerProps());
    }


    public void setProducer(Map<String, Object> producer) {
        this.producer = producer;
    }
}
*/

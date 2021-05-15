//package bd.ac.buet.KeyValueStore.configuration;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//
//import java.util.Collections;
//
//@Configuration
//@Slf4j
//public class KafkaConfiguration {
////    private final KafkaProperties kafkaProperties;
////
////    public KafkaConfiguration(KafkaProperties kafkaProperties) {
////        this.kafkaProperties = kafkaProperties;
////    }
//
//   /* @Bean
//    public KafkaTemplate<String, Object> kafkaTemplate(){
//        return new KafkaTemplate<>();
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(){
//        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(kafkaProperties.consumerFactory());
//        return factory;
//    }*/
//}

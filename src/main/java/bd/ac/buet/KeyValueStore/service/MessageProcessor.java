package bd.ac.buet.KeyValueStore.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class MessageProcessor {

    private KafkaTemplate<String, String> kafkaTemplate;

    public MessageProcessor(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message){
        kafkaTemplate.send(topic, message);
    }

    @Async
    public void testSendMessage() {
        for(int i=0; i<100; i++){
            sendMessage("paxos-topic", "hellos "+i);
            //Thread.sleep(5000);
        }
    }

    @KafkaListener(topics = "paxos-topic")
    public void listenToPaxosTopic(String message){
      log.info("in the listener");
      log.info(message);
    }

}

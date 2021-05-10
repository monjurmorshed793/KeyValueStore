package bd.ac.buet.KeyValueStore.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;
import reactor.kafka.sender.SenderResult;

@Service
public class MessageProcessor {
    private final KafkaSender kafkaSender;

    public MessageProcessor(KafkaSender kafkaSender) {
        this.kafkaSender = kafkaSender;
    }

    public void publish(String topic, String message){
        Mono
                .just(SenderRecord.create(topic, null, null, null, message, null))
                .as(kafkaSender::send)
                .subscribe();
    }


    public void listenToMessage(){

    }
}

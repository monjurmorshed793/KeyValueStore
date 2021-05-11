package bd.ac.buet.KeyValueStore.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;
import reactor.kafka.sender.SenderResult;

@Service
@Slf4j
public class MessageProcessor {
    private final KafkaSender kafkaSender;
    private final KafkaReceiver kafkaReceiver;

    public MessageProcessor(KafkaSender kafkaSender, KafkaReceiver kafkaReceiver) {
        this.kafkaSender = kafkaSender;
        this.kafkaReceiver = kafkaReceiver;
    }

    public void publish(String topic, String message){
        Mono
                .just(SenderRecord.create(topic, null, null, null, message, null))
                .as(kafkaSender::send)
                .subscribe();
    }


    @Async
    public void listenToMessage(String topic){
        kafkaReceiver.receive().subscribe((c)->{
           log.info("**************************");
           log.info(c.toString());
        });
    }

    @Async
    public void testKafkaMessages() throws InterruptedException {
        for(int i=1; i<100; i++){
          log.info("sending message: {}", i);
          publish("paxos-topic", "hello from munna "+i);
          Thread.sleep(1000);
        }
    }
}

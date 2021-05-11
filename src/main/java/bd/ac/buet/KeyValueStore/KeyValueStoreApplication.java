package bd.ac.buet.KeyValueStore;

import bd.ac.buet.KeyValueStore.service.MessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@Slf4j
public class KeyValueStoreApplication {

	@Autowired
	private MessageProcessor messageProcessor;

	public static void main(String[] args) {
		SpringApplication.run(KeyValueStoreApplication.class, args);
	}

	@PostConstruct
	public void sendKafkaMessage() throws InterruptedException {
		log.info("Sending kafka message post construct");
		//messageProcessor.publish("paxos-topic", "hello from munna23");
		messageProcessor.listenToMessage("paxos-topic");
		messageProcessor.testKafkaMessages();
	}

}

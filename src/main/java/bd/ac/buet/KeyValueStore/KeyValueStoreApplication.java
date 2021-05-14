package bd.ac.buet.KeyValueStore;

import bd.ac.buet.KeyValueStore.configuration.KafkaProducer;
import bd.ac.buet.KeyValueStore.service.MessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@Slf4j
public class KeyValueStoreApplication {

	@Autowired
	private KafkaProducer kafkaProducer;
	@Value("${application.name}")
	private String applicationName;

	public static void main(String[] args) {
		SpringApplication.run(KeyValueStoreApplication.class, args);
	}

	@PostConstruct
	public void sendKafkaMessage() throws InterruptedException {
		kafkaProducer.send("paxos-topic",applicationName);
	}

}

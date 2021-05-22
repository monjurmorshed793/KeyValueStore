package bd.ac.buet.KeyValueStore;

import bd.ac.buet.KeyValueStore.service.KafkaProducer;
import bd.ac.buet.KeyValueStore.service.MessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@EnableEurekaClient
@SpringBootApplication
@EnableFeignClients
public class KeyValueStoreApplication {


	public static void main(String[] args) {
		SpringApplication.run(KeyValueStoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner initiate(MessageProcessor messageProcessor,@Value("${application.name}") String applicationName){
		return (args -> {
			messageProcessor.initializeApplicationAndBroadcast();
		});
	}



}

package bd.ac.buet.KeyValueStore.service;

import bd.ac.buet.KeyValueStore.configuration.ApplicationProperties;
import bd.ac.buet.KeyValueStore.model.ApplicationInfo;
import bd.ac.buet.KeyValueStore.repository.ApplicationInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;


@Service
@Slf4j
@Transactional
public class MessageProcessor {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String applicationName;
    private final ApplicationInfoRepository applicationInfoRepository;
    private final ApplicationInfoService applicationInfoService;
    private final ApplicationProperties applicationProperties;
    private final ServerInfoService serverInfoService;

    public MessageProcessor(KafkaTemplate<String, Object> kafkaTemplate,
                            @Value("${application.name}") String applicationName,
                            ApplicationInfoRepository applicationInfoRepository,
                            ApplicationInfoService applicationInfoService,
                            ApplicationProperties applicationProperties, ServerInfoService serverInfoService) {
        this.kafkaTemplate = kafkaTemplate;
        this.applicationName = applicationName;
        this.applicationInfoRepository = applicationInfoRepository;
        this.applicationInfoService = applicationInfoService;
        this.applicationProperties = applicationProperties;
        this.serverInfoService = serverInfoService;
    }

    public void initializeApplicationAndBroadcast(){
        log.info("initializing application info");
        applicationInfoRepository.deleteAll();
        ApplicationInfo applicationInfo = ApplicationInfo.builder()
                .name(this.applicationName)
                .createdOn(Instant.now())
                .updatedOn(Instant.now())
                .build();
        applicationInfo = applicationInfoRepository.save(applicationInfo);
        kafkaTemplate.send("server-info", applicationInfoService.convert(applicationInfo));
    }


    @Scheduled(fixedRate = 10000) // broadcasting at 10s intervals
    public void scheduledServerInfoBroadCase(){
        kafkaTemplate.send("server-info", serverInfoService.getSelfServerInfo());
    }

}

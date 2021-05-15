package bd.ac.buet.KeyValueStore.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class ApplicationProperties {
    @Value("${application.topic}")
    private String applicationTopic;


    public String getApplicationTopic() {
        return applicationTopic;
    }

}

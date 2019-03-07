package integration.configuration;

import client.MckingBurguerClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@Slf4j
@TestConfiguration
public class MckingBurguerTestConfiguration {

    @Bean
    public MckingBurguerClient client(@Qualifier("testRestTemplate") RestTemplate client) {
        return new MckingBurguerClient(client);
    }

    @Bean(name = "testRestTemplate")
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
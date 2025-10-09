package dev.sudu.productserviceoct7.configs;

import dev.sudu.productserviceoct7.properties.ApplicationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate getRestTemplate(ApplicationProperties properties) {
        return new RestTemplate();
    }

//    @Bean
//    @LoadBalanced
//    public RestClient restClient() {
//        return RestClient.create();
//    }

    @Bean
    @LoadBalanced
    public RestClient.Builder loadBalancedRestClientBuilder() {
        return RestClient.builder();
    }
}

package com.sep.scientificcentre.scientificcentre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class ScientificCentreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScientificCentreApplication.class, args);
    }

//    @Bean
//    @ConfigurationProperties("security.oauth2.client")
//    protected ClientCredentialsResourceDetails oAuthDetails() {
//        return new ClientCredentialsResourceDetails();
//    }

//    @Bean
//    protected RestTemplate restTemplate() {
//        return new OAuth2RestTemplate(oAuthDetails());
//    }
}

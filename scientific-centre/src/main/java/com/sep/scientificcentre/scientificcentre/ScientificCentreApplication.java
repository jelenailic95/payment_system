package com.sep.scientificcentre.scientificcentre;

import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EncryptablePropertySource("application.properties")
public class ScientificCentreApplication {

    public static void main(String[] args) {
        System.setProperty("jasypt.encryptor.password", "supersecretz");

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

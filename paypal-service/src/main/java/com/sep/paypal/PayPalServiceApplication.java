package com.sep.paypal;

import com.paypal.base.rest.APIContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@SpringBootApplication
public class PayPalServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayPalServiceApplication.class, args);
    }
    @Bean
    public APIContext apiContext() {
        return new APIContext();
    }
}

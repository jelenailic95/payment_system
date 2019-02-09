package com.sep.authservice;

import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EncryptablePropertySource("application.properties")
public class AuthServiceApplication {

    public static void main(String[] args) {
        System.setProperty("jasypt.encryptor.password", "supersecretz");

        SpringApplication.run(AuthServiceApplication.class, args);
    }

}


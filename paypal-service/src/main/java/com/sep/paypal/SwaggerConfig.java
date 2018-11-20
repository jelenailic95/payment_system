package com.sep.paypal;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger tool configuration.
 *
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Filtering API to be included in Swagger's response/scope.
     *
     * @return
     */
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(SwaggerConfig.class.getPackage().getName()))
                .paths(PathSelectors.any())
                .build();
    }



}

package com.sep.proxy.proxy;

import com.sep.proxy.proxy.security.ZuulSSL;
import org.apache.http.conn.HttpClientConnectionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.commons.httpclient.ApacheHttpClientConnectionManagerFactory;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class ProxyServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProxyServerApplication.class, args);
    }

    @Bean
    @ConditionalOnMissingBean(HttpClientConnectionManager.class)
    public HttpClientConnectionManager connectionManager(
            ApacheHttpClientConnectionManagerFactory connectionManagerFactory,
            ZuulProperties zuulProperties) {
        /**
         * Fixing #2503 by not disabling ssl validation when sslHostnameValidationEnabled=false
         */
        ZuulProperties.Host hostProperties = zuulProperties.getHost();
        final HttpClientConnectionManager connectionManager = connectionManagerFactory
                .newConnectionManager(false,
                        hostProperties.getMaxTotalConnections(),
                        hostProperties.getMaxPerRouteConnections(),
                        hostProperties.getTimeToLive(), hostProperties.getTimeUnit(),
                        null);
        return connectionManager;
    }

    @Bean
    @ConditionalOnMissingBean
    public ApacheHttpClientConnectionManagerFactory connManFactory() {
        return new ZuulSSL();
    }
}

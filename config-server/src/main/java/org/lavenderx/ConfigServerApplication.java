package org.lavenderx;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
@EnableOAuth2Client
@Slf4j
public class ConfigServerApplication implements GenericApplicationListener {

    @Override
    public boolean supportsEventType(ResolvableType eventType) {
        return true;
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return true;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

    }

    @Override
    public int getOrder() {
        return 0;
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .web(true)
                .sources(ConfigServerApplication.class)
                .registerShutdownHook(true)
                .run(args);
    }
}

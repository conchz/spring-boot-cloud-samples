package org.lavenderx;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class Client implements CommandLineRunner {

    @Autowired
    private DiscoveryClient discovery;

    @Override
    public void run(String... args) throws Exception {
        log.info("Discovery services: {}", discovery.getServices());
    }

    public static void main(String[] args) {
        SpringApplication.run(Client.class, args);
    }
}

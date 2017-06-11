package org.lavenderx.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ConfigServerController extends BaseController {

    @RequestMapping("/jetty-service1/{profile}/{label}")
    public ResponseEntity<Environment> service1(@PathVariable String profile, @PathVariable String label) {
        Environment env = new Environment("jetty-service1", profile);
        env.setLabel(label);
        env.setVersion("V1");

        return ResponseEntity.ok(env);
    }

    @RequestMapping("/jetty-service2/{profile}/{label}")
    public ResponseEntity<Environment> service2(@PathVariable String profile, @PathVariable String label) {
        Environment env = new Environment("jetty-service2", profile);
        env.setLabel(label);
        env.setVersion("V1");

        return ResponseEntity.ok(env);
    }

    @RequestMapping("/jetty-client1/{profile}/{label}")
    public ResponseEntity<Environment> client1(@PathVariable String profile, @PathVariable String label) {
        Environment env = new Environment("jetty-client1", profile);
        env.setLabel(label);
        env.setVersion("V1");

        return ResponseEntity.ok(env);
    }
}

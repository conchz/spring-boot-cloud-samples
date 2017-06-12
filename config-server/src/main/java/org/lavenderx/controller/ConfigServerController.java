package org.lavenderx.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ConfigServerController extends BaseController {

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> index() {
        String content = "{\"status\": \"UP\"}";
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        return new ResponseEntity<>(content, responseHeaders, HttpStatus.OK);
    }

    @RequestMapping("/service")
    public ResponseEntity<String> service() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden");
    }

    @RequestMapping("/client")
    public ResponseEntity<String> client() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden");
    }
}

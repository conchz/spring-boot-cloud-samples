package org.lavenderx;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.HashMap;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@Slf4j
public class Client {
    public static void main(String[] args) {
        SpringApplication.run(Client.class, args);
    }

    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    private DiscoveryClient discovery;

    @RequestMapping("/discovery")
    public Object discovery() {
        log.info("{}", loadBalancer.choose("jetty-service1"));
        return "discovery";
    }

    @RequestMapping("/all")
    public Object all() {
        log.info("{}", discovery.getServices());
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(new HashMap<String, Object>() {{
            put("services", discovery.getServices());
        }});

        return new ModelAndView(jsonView);
    }
}

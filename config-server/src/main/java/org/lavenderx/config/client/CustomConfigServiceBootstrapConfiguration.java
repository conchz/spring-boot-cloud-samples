package org.lavenderx.config.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Configuration
public class CustomConfigServiceBootstrapConfiguration {

    @Value("${spring.application.name:application}")
    private String name;
    @Value("${spring.cloud.config.uri}")
    private String uri;
    @Value("${spring.cloud.config.username}")
    private String username;
    @Value("${spring.cloud.config.password}")
    private String password;

    @Autowired
    private Environment environment;

    @Bean
    public ConfigClientProperties configClientProperties() {
        ConfigClientProperties client = new ConfigClientProperties(this.environment);
        client.setEnabled(false);
        client.setName(name);
        client.setUri(uri);
        client.setUsername(username);
        client.setPassword(password);

        ConfigClientProperties.Discovery discovery = new ConfigClientProperties.Discovery();
        discovery.setEnabled(true);
        client.setDiscovery(discovery);

        return client;
    }

    @Bean
    public ConfigServicePropertySourceLocator configServicePropertySourceLocator() {
        ConfigClientProperties clientProperties = configClientProperties();
        ConfigServicePropertySourceLocator configServicePropertySourceLocator = new ConfigServicePropertySourceLocator(clientProperties);
        configServicePropertySourceLocator.setRestTemplate(customRestTemplate(clientProperties));
        return configServicePropertySourceLocator;
    }

    private RestTemplate customRestTemplate(ConfigClientProperties properties) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(60 * 1000);
        requestFactory.setReadTimeout((60 * 1000 * 3) + 5000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        String username = properties.getUsername();
        String password = properties.getPassword();
        Map<String, String> headers = new HashMap<>(properties.getHeaders());
        if (!headers.isEmpty() && headers.containsKey(AUTHORIZATION)) {
            String authorization = headers.get(AUTHORIZATION);
            if (Objects.nonNull(password) && !StringUtils.isEmpty(authorization)) {
                throw new IllegalStateException("You must set either 'password' or 'authorization'");
            }
        }

        if (headers.isEmpty() || !headers.containsKey(AUTHORIZATION)) {
            byte[] token = Base64Utils.encode((username + ":" + password).getBytes());
            headers.put(AUTHORIZATION, "Basic " + new String(token));
        }

        if (!headers.isEmpty()) {
            restTemplate.setInterceptors(Arrays.asList(
                    new ConfigServicePropertySourceLocator.GenericRequestHeaderInterceptor(headers)));
        }

        return restTemplate;
    }
}
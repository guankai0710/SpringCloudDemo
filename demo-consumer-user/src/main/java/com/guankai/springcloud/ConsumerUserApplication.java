package com.guankai.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 服务生产者启动类
 *
 * @author guan.kai
 * @date 2020-03-27 22:00
 */
@SpringBootApplication
public class ConsumerUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerUserApplication.class, args);
    }

    /**
     * 注入restTemplate
     * @return
     */
    @Bean(name = "restTemplate")
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }
}

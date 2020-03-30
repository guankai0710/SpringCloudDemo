package com.guankai.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 服务生产者启动类
 * 开启事务管理
 *
 * @author guan.kai
 * @date 2020-03-27 22:00
 */
@SpringBootApplication
@EnableTransactionManagement
public class ProviderUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderUserApplication.class, args);
    }
}

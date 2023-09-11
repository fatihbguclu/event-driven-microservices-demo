package com.ft.kafka.elastic.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.ft"})
public class KafkaToElasticServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaToElasticServiceApplication.class, args);
    }
}

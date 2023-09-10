package com.ft.twittertokafkaservice;

import com.ft.twittertokafkaservice.init.StreamInitializer;
import com.ft.twittertokafkaservice.runner.StreamRunner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.ft.*")
@Slf4j
@RequiredArgsConstructor
public class TwitterToKafkaServiceApplication implements CommandLineRunner {

	private final StreamRunner streamRunner;

	private final StreamInitializer streamInitializer;

	public static void main(String[] args) {
		SpringApplication.run(TwitterToKafkaServiceApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		log.info("App starts...");
		streamInitializer.init();
		streamRunner.start();
	}
}

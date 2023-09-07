package com.ft.twittertokafkaservice;

import com.ft.appconfigdata.TwitterToKafkaServiceConfigData;
import com.ft.twittertokafkaservice.runner.StreamRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication(scanBasePackages = "com.ft")
@Slf4j
public class TwitterToKafkaServiceApplication implements CommandLineRunner {
	private final TwitterToKafkaServiceConfigData twitterToKafkaServiceConfigData;
	private final StreamRunner streamRunner;

	public TwitterToKafkaServiceApplication(TwitterToKafkaServiceConfigData twitterToKafkaServiceConfigData, StreamRunner streamRunner) {
		this.twitterToKafkaServiceConfigData = twitterToKafkaServiceConfigData;
		this.streamRunner = streamRunner;
	}

	public static void main(String[] args) {
		SpringApplication.run(TwitterToKafkaServiceApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		log.info(Arrays.toString(twitterToKafkaServiceConfigData.getTwitterKeywords().toArray(new String[0])));
		log.info(twitterToKafkaServiceConfigData.getWelcomeMessage());
		streamRunner.start();
	}
}

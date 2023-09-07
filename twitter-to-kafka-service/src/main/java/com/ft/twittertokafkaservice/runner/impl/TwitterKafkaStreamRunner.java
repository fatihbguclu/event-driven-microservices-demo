package com.ft.twittertokafkaservice.runner.impl;

import com.ft.appconfigdata.TwitterToKafkaServiceConfigData;
import com.ft.twittertokafkaservice.listener.TwitterKafkaStatusListener;
import com.ft.twittertokafkaservice.runner.StreamRunner;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import twitter4j.FilterQuery;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import java.util.Arrays;

@Component
@Slf4j
@ConditionalOnProperty(name = "twitter-to-kafka-service.enable-mock-tweets", havingValue = "false")
public class TwitterKafkaStreamRunner implements StreamRunner {

    private final TwitterToKafkaServiceConfigData configData;

    private final TwitterKafkaStatusListener listener;

    private TwitterStream twitterStream;

    public TwitterKafkaStreamRunner(TwitterToKafkaServiceConfigData configData,
                                    TwitterKafkaStatusListener listener) {
        this.configData = configData;
        this.listener = listener;
    }

    @Override
    public void start() throws TwitterException {
        twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener((listener));
        addFilter();
    }

    @PreDestroy
    public void shutdown() {
        if (twitterStream != null){
            log.info("Closing twitter stream.");
            twitterStream.shutdown();
        }
    }

    private void addFilter() {
        String[] keywords = configData.getTwitterKeywords().toArray(new String[0]);
        FilterQuery filterQuery = new FilterQuery(keywords);
        twitterStream.filter(filterQuery);
        log.info("Started filtering twitter stream for keywords {}", Arrays.toString(keywords));
    }
}

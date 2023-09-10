package com.ft.twittertokafkaservice.listener;

import com.ft.appconfigdata.KafkaConfigData;
import com.ft.kafka.avro.model.TwitterAvroModel;
import com.ft.kafka.producer.config.service.KafkaProducer;
import com.ft.twittertokafkaservice.transformer.TwitterStatusToAvroTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.StatusAdapter;

@Component
@Slf4j
@RequiredArgsConstructor
public class TwitterKafkaStatusListener extends StatusAdapter {

    private final KafkaConfigData kafkaConfigData;

    private final KafkaProducer<Long, TwitterAvroModel> kafkaProducer;

    private final TwitterStatusToAvroTransformer transformer;

    @Override
    public void onStatus(Status status) {
        log.info("Twitter status with text {}", status.getText());
        TwitterAvroModel model = transformer.getTwitterAvroModelFromStatus(status);
        kafkaProducer.send(kafkaConfigData.getTopicName(), model.getId(), model);
    }
}

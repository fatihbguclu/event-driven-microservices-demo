package com.ft.kafka.elastic.service.consumer.impl;

import com.ft.appconfigdata.KafkaConfigData;
import com.ft.elastic.index.client.service.ElasticIndexClient;
import com.ft.elastic.model.TwitterIndexModel;
import com.ft.kafka.admin.client.KafkaAdminClient;
import com.ft.kafka.avro.model.TwitterAvroModel;
import com.ft.kafka.elastic.service.consumer.KafkaConsumer;
import com.ft.kafka.elastic.service.transformer.AvroToElasticModelTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TwitterKafkaConsumer implements KafkaConsumer<Long, TwitterAvroModel> {

    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    private final KafkaAdminClient kafkaAdminClient;

    private final KafkaConfigData kafkaConfigData;

    private final AvroToElasticModelTransformer transformer;

    private final ElasticIndexClient<TwitterIndexModel> elasticIndexClient;

    @EventListener
    public void onAppStarted(ApplicationStartedEvent event){
        kafkaAdminClient.checksTopicsCreated();
        log.info("Topics with name {} is ready for operations!", kafkaConfigData.getTopicNamesToCreate().toArray());
        kafkaListenerEndpointRegistry.getListenerContainer("twitterTopicListener").start();
    }

    @Override
    @KafkaListener(id = "twitterTopicListener", topics = "${kafka-config.topic-name}")
    public void receive(@Payload List<TwitterAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<Integer> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of message received with keys {}, partitions {} and offsets {}, " +
                        "sending it to elastic: Thread id {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString(),
                Thread.currentThread().getId()
        );
        List<TwitterIndexModel> twitterIndexModels = transformer.getElasticModels(messages);
        List<String> documentIds = elasticIndexClient.save(twitterIndexModels);
        log.info("Documents saved to elasticsearch with ids {}", documentIds.toArray());
    }
}

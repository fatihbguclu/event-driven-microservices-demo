package com.ft.kafka.streams.service.init;

import com.ft.appconfigdata.KafkaConfigData;
import com.ft.kafka.admin.client.KafkaAdminClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaStreamsInitializer implements StreamsInitializer {
    private final KafkaConfigData kafkaConfigData;

    private final KafkaAdminClient kafkaAdminClient;


    @Override
    public void init() {
        kafkaAdminClient.checksTopicsCreated();
        kafkaAdminClient.checkSchemaRegistry();
        log.info("Topics with name {} is ready for operations!", kafkaConfigData.getTopicNamesToCreate().toArray());
    }
}

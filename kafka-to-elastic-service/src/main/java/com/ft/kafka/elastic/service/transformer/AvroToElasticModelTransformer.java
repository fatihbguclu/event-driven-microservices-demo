package com.ft.kafka.elastic.service.transformer;

import com.ft.elastic.model.TwitterIndexModel;
import com.ft.kafka.avro.model.TwitterAvroModel;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AvroToElasticModelTransformer {

    public List<TwitterIndexModel> getElasticModels(List<TwitterAvroModel> twitterAvroModels) {
        return twitterAvroModels.stream()
                .map(avroModel -> TwitterIndexModel.builder()
                        .id(String.valueOf(avroModel.getId()))
                        .userId(avroModel.getUserId())
                        .text(avroModel.getText())
                        .createdAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(avroModel.getCreatedAt()), ZoneId.systemDefault()))
                        .build()
                ).collect(Collectors.toList());
    }

}

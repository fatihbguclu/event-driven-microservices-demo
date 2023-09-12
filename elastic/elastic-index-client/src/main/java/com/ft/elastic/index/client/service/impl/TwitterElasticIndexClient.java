package com.ft.elastic.index.client.service.impl;

import com.ft.appconfigdata.ElasticConfigData;
import com.ft.elastic.index.client.service.ElasticIndexClient;
import com.ft.elastic.index.client.util.ElasticIndexUtil;
import com.ft.elastic.model.TwitterIndexModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ConditionalOnProperty(name = "elastic-config.is-repository", havingValue = "false")
@RequiredArgsConstructor
@Slf4j
public class TwitterElasticIndexClient implements ElasticIndexClient<TwitterIndexModel> {

    private final ElasticConfigData elasticConfigData;

    private final ElasticsearchOperations elasticsearchOperations;

    private final ElasticIndexUtil<TwitterIndexModel> elasticIndexUtil;

    @Override
    public List<String> save(List<TwitterIndexModel> document) {
        List<IndexQuery> indexQueries = elasticIndexUtil.getIndexQueries(document);

        return elasticsearchOperations.bulkIndex(
                indexQueries,
                IndexCoordinates.of(elasticConfigData.getIndexName())
            ).stream()
                .map(IndexedObjectInformation::id)
                .toList();
    }
}

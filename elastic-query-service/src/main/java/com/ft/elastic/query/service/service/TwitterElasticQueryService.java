package com.ft.elastic.query.service.service;

import com.ft.elastic.model.TwitterIndexModel;
import com.ft.elastic.query.client.service.ElasticQueryClient;
import com.ft.elastic.query.service.common.model.ElasticQueryServiceResponseModel;
import com.ft.elastic.query.service.model.assembler.ElasticQueryServiceResponseModelAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class TwitterElasticQueryService implements ElasticQueryService {


    private final ElasticQueryServiceResponseModelAssembler elasticQueryServiceResponseModelAssembler;

    private final ElasticQueryClient<TwitterIndexModel> elasticQueryClient;

    public TwitterElasticQueryService(ElasticQueryServiceResponseModelAssembler assembler,
                                      ElasticQueryClient<TwitterIndexModel> queryClient) {
        this.elasticQueryServiceResponseModelAssembler = assembler;
        this.elasticQueryClient = queryClient;
    }

    @Override
    public ElasticQueryServiceResponseModel getDocumentById(String id) {
        log.info("Querying elasticsearch by id {}", id);
        return elasticQueryServiceResponseModelAssembler.toModel(elasticQueryClient.getIndexModelById(id));
    }

    @Override
    public List<ElasticQueryServiceResponseModel> getDocumentByText(String text) {
        log.info("Querying elasticsearch by text {}", text);
        return elasticQueryServiceResponseModelAssembler.toModels(elasticQueryClient.getIndexModelByText(text));
    }

    @Override
    public List<ElasticQueryServiceResponseModel> getAllDocuments() {
        log.info("Querying all documents in elasticsearch");
        return elasticQueryServiceResponseModelAssembler.toModels(elasticQueryClient.getAllIndexModels());
    }
}

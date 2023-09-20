package com.ft.reactive.elastic.query.web.client.service;

import com.ft.elastic.query.web.client.common.model.ElasticQueryWebClientRequestModel;
import com.ft.elastic.query.web.client.common.model.ElasticQueryWebClientResponseModel;
import reactor.core.publisher.Flux;

public interface ElasticQueryWebClient {

    Flux<ElasticQueryWebClientResponseModel> getDataByText(ElasticQueryWebClientRequestModel request);
}
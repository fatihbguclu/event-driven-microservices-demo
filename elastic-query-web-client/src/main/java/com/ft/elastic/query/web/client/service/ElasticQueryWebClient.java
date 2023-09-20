package com.ft.elastic.query.web.client.service;

import com.ft.elastic.query.web.client.common.model.ElasticQueryWebClientRequestModel;
import com.ft.elastic.query.web.client.common.model.ElasticQueryWebClientResponseModel;

import java.util.List;

public interface ElasticQueryWebClient {

    List<ElasticQueryWebClientResponseModel> getDataByText(ElasticQueryWebClientRequestModel requestModel);
}

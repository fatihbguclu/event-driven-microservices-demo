package com.ft.elastic.query.client.service;

import com.ft.elastic.model.IndexModel;

import java.util.List;

public interface ElasticQueryClient<T extends IndexModel> {
    T getIndexModelById(String id);

    List<T> getIndexModelByText(String text);

    List<T> getAllIndexModels();
}

package com.ft.elastic.index.client.service;

import com.ft.elastic.model.IndexModel;

import java.util.List;

public interface ElasticIndexClient<T extends IndexModel> {
    List<String> save(List<T> document);
}

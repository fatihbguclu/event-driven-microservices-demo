package com.ft.reactive.elastic.query.service.service;

import com.ft.elastic.model.IndexModel;
import com.ft.elastic.model.TwitterIndexModel;
import reactor.core.publisher.Flux;

public interface ReactiveElasticQueryClient<T extends IndexModel> {

    Flux<TwitterIndexModel> getIndexModelByText(String text);
}


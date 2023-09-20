package com.ft.reactive.elastic.query.web.client.service;

import com.ft.appconfigdata.ElasticQueryWebClientConfigData;
import com.ft.elastic.query.web.client.common.exception.ElasticQueryWebClientException;
import com.ft.elastic.query.web.client.common.model.ElasticQueryWebClientRequestModel;
import com.ft.elastic.query.web.client.common.model.ElasticQueryWebClientResponseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class TwitterElasticQueryWebClient implements ElasticQueryWebClient {

    private final WebClient webClient;

    private final ElasticQueryWebClientConfigData elasticQueryWebClientConfig;

    @Override
    public Flux<ElasticQueryWebClientResponseModel> getDataByText(ElasticQueryWebClientRequestModel requestModel) {
        log.info("Querying by text {}", requestModel.getText());
        return getWebClient(requestModel)
                .bodyToFlux(ElasticQueryWebClientResponseModel.class);
    }

    private WebClient.ResponseSpec getWebClient(ElasticQueryWebClientRequestModel requestModel) {
        return webClient
                .method(HttpMethod.valueOf(elasticQueryWebClientConfig.getQueryByText().getMethod()))
                .uri(elasticQueryWebClientConfig.getQueryByText().getUri())
                .accept(MediaType.valueOf(elasticQueryWebClientConfig.getQueryByText().getAccept()))
                .body(BodyInserters.fromPublisher(Mono.just(requestModel), createParameterizedTypeReference()))
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.equals(HttpStatus.UNAUTHORIZED),
                        clientResponse -> Mono.just(new BadCredentialsException("Not authenticated!")))
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        cr -> Mono.just(new ElasticQueryWebClientException(String.valueOf(cr.statusCode().value()))))
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        cr -> Mono.just(new Exception(String.valueOf(cr.statusCode().value()))));
    }


    private <T> ParameterizedTypeReference<T> createParameterizedTypeReference() {
        return new ParameterizedTypeReference<>() {
        };
    }
}

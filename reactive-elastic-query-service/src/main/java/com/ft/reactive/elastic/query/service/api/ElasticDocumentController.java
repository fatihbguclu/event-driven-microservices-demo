package com.ft.reactive.elastic.query.service.api;

import com.ft.elastic.query.service.common.model.ElasticQueryServiceRequestModel;
import com.ft.elastic.query.service.common.model.ElasticQueryServiceResponseModel;
import com.ft.reactive.elastic.query.service.service.ElasticQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(value = "/documents")
@Slf4j
@RequiredArgsConstructor
public class ElasticDocumentController {

    private final ElasticQueryService elasticQueryService;

    @PostMapping(value = "/get-doc-by-text",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ElasticQueryServiceResponseModel> getDocumentByText(
            @RequestBody @Valid ElasticQueryServiceRequestModel requestModel) {
        Flux<ElasticQueryServiceResponseModel> response =
                elasticQueryService.getDocumentByText(requestModel.getText());
        response = response.log();
        log.info("Returning from query reactive service for text {}!", requestModel.getText());
        return response;
    }
}

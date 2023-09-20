package com.ft.elastic.query.web.client.config;

import com.ft.appconfigdata.ElasticQueryWebClientConfigData;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class ElasticQueryServiceInstanceListSupplierConfig implements ServiceInstanceListSupplier {

    private final ElasticQueryWebClientConfigData.WebClient webClientConfig;

    @Override
    public String getServiceId() {
        return webClientConfig.getServiceId();
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        return Flux.just(
                webClientConfig.getInstances().stream()
                        .map(instance ->
                                new DefaultServiceInstance(
                                        instance.getId(),
                                        getServiceId(),
                                        instance.getHost(),
                                        instance.getPort(),
                                        false
                                )).collect(Collectors.toList())
        );
    }
}

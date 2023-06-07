package com.BjitMidTerm.Bookservice.config;

import com.BjitMidTerm.Bookservice.client.InventoryClient;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {
    private final LoadBalancedExchangeFilterFunction exchangeFilterFunction;
    public WebClient inventoryWebClient(){
        return WebClient.builder().baseUrl("http://inventory-service")
                .filter(exchangeFilterFunction)
                .build();
    }
    @Bean
    public InventoryClient inventoryClient(){
        HttpServiceProxyFactory httpServiceProxyFactory=HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(inventoryWebClient()))
                .build();
        return  httpServiceProxyFactory.createClient(InventoryClient.class);
    }
}

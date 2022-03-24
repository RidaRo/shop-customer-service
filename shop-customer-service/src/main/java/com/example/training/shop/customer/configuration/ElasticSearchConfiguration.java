package com.example.training.shop.customer.configuration;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@ComponentScan(basePackages = {"com.example.training.shop.customer"})
@EnableElasticsearchRepositories(basePackages = {"com.example.training.shop.customer.repositories"})
public class ElasticSearchConfiguration {
    @Value("${elasticsearch.url}")
    private String elasticSearchUrl;

    public RestHighLevelClient client() {
        ClientConfiguration configuration = ClientConfiguration.builder()
                .connectedTo(elasticSearchUrl).build();
        return RestClients.create(configuration).rest();
    }
}


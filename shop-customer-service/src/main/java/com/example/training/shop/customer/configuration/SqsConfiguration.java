package com.example.training.shop.customer.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;

@Configuration
public class SqsConfiguration {
    private final String region;
    private final String sqsEndpoint;

    public SqsConfiguration(@Value("${aws.region}") String region,
                            @Value("${sqs.endpoint}") String sqsEndpoint) {
        this.region = region;
        this.sqsEndpoint = sqsEndpoint;
    }

    @Bean
    public SqsClient sqsClient() {
        return SqsClient.builder()
                .endpointOverride(URI.create(sqsEndpoint))
                .region(Region.of(region))
                .build();
    }
}

package com.example.training.shop.customer.updaters;

import com.example.training.shop.customer.dto.ItemDTO;
import com.example.training.shop.customer.feign.KeeperClient;
import com.example.training.shop.customer.models.Item;
import com.example.training.shop.customer.repositories.ElasticSearchRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.List;

@Component
@Slf4j
public class UpdateScheduler {

    private final long delay = 5000;
    private final SqsUpdater sqsUpdater;
    private final KeeperClient keeperClient;
    private final ElasticSearchRepository elasticSearchRepository;
    private final ObjectMapper objectMapper;

    public UpdateScheduler(SqsUpdater sqsUpdater,
                           KeeperClient keeperClient,
                           ElasticSearchRepository elasticSearchRepository,
                           ObjectMapper objectMapper) {
        this.sqsUpdater = sqsUpdater;
        this.keeperClient = keeperClient;
        this.elasticSearchRepository = elasticSearchRepository;
        this.objectMapper = objectMapper;
    }

    static class SqsMessage {
        @JsonProperty("Message")
        private String message;
    }

    @Scheduled(fixedDelay = delay)
    public void updateMessage() {
        List<Message> messages = sqsUpdater.pullMessage();

        messages.forEach(message -> {
            try {
                SqsMessage sqsMessage = objectMapper.readValue(message.body(), SqsMessage.class);
                ItemDTO item = keeperClient.getItemByCode(sqsMessage.message);
                elasticSearchRepository.save(new Item(item));
                sqsUpdater.queueCleanup(messages);
            } catch (JsonProcessingException e) {
                log.error("Can't convert message", e);
            }
        });
    }
}

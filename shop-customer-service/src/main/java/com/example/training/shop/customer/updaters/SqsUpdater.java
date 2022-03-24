package com.example.training.shop.customer.updaters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;

import java.util.List;

@Component
public class SqsUpdater {
    private final SqsClient sqsClient;
    private final String queueUrl;

    @Autowired
    public SqsUpdater(SqsClient sqsClient,
                      @Value("${sqs.queue.url}") String queueUrl) {
        this.sqsClient = sqsClient;
        this.queueUrl = queueUrl;
    }

    public List<Message> pullMessage() {
        ReceiveMessageRequest build = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .build();
        ReceiveMessageResponse receiveMessageResponse = sqsClient.receiveMessage(build);
        return receiveMessageResponse.messages();
    }

    public void queueCleanup(List<Message> messages) {
        messages.forEach(message -> {
            DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .receiptHandle(message.receiptHandle())
                    .build();
            sqsClient.deleteMessage(deleteMessageRequest);
        });
    }
}

package com.example.training.shop.customer.controller;

import com.example.training.shop.customer.updaters.SqsUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class MainController {
    private final SqsUpdater sqsUpdater;

    @Autowired
    public MainController(SqsUpdater sqsUpdater) {
        this.sqsUpdater = sqsUpdater;
    }

    @GetMapping("/pull")
    public List<String> pullMessages() {
        return sqsUpdater.pullMessage().stream()
                .map(Message::toString)
                .collect(Collectors.toList());
    }
}

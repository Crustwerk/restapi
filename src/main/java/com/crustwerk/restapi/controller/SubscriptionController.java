package com.crustwerk.restapi.controller;

import com.crustwerk.restapi.dto.subscription.request.CreateSubscriptionRequest;
import com.crustwerk.restapi.dto.subscription.request.GetSubscriptionBetweenDatesRequest;
import com.crustwerk.restapi.dto.subscription.response.CreateSubscriptionResponse;
import com.crustwerk.restapi.dto.subscription.response.GetSubscriptionResponse;
import com.crustwerk.restapi.mapper.SubscriptionMapper;
import com.crustwerk.restapi.model.Subscription;
import com.crustwerk.restapi.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/subscriptions")
@Validated
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final SubscriptionMapper subscriptionMapper;

    public SubscriptionController(SubscriptionService subscriptionService, SubscriptionMapper subscriptionMapper) {
        this.subscriptionService = subscriptionService;
        this.subscriptionMapper = subscriptionMapper;
    }

    @PostMapping
    public ResponseEntity<CreateSubscriptionResponse> createSubscription(@Valid @RequestBody CreateSubscriptionRequest req) {
        Subscription subscription = subscriptionMapper.toModel(req);
        Subscription saved = subscriptionService.createSubscription(subscription);

        CreateSubscriptionResponse response = subscriptionMapper.toCreateSubscriptionResponse(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSubscriptionResponse> getSubscriptionById(@Valid @PathVariable Long id) {
        Optional<Subscription> subscriptionOptional = subscriptionService.getSubscriptionById(id);
        return subscriptionOptional
                .map(s -> ResponseEntity.ok(subscriptionMapper.toGetSubscriptionResponse(s)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Subscription>> getSubscriptionsBetweenDates(@Valid @RequestBody GetSubscriptionBetweenDatesRequest req) {
        List<Subscription> subscriptions = subscriptionService.getSubscriptionBetweenDates(req.start(), req.end());
        return ResponseEntity.ok(subscriptions);
    }
}

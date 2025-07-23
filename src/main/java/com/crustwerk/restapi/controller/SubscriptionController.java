package com.crustwerk.restapi.controller;

import com.crustwerk.restapi.Utils;
import com.crustwerk.restapi.dto.subscription.request.CreateSubscriptionRequest;
import com.crustwerk.restapi.dto.subscription.request.GetSubscriptionBetweenDatesRequest;
import com.crustwerk.restapi.dto.subscription.response.CreateSubscriptionResponse;
import com.crustwerk.restapi.dto.subscription.response.GetSubscriptionResponse;
import com.crustwerk.restapi.mapper.SubscriptionMapper;
import com.crustwerk.restapi.model.Subscription;
import com.crustwerk.restapi.model.SubscriptionDuration;
import com.crustwerk.restapi.model.SubscriptionTier;
import com.crustwerk.restapi.service.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final SubscriptionMapper subscriptionMapper;

    public SubscriptionController(SubscriptionService subscriptionService, SubscriptionMapper subscriptionMapper) {
        this.subscriptionService = subscriptionService;
        this.subscriptionMapper = subscriptionMapper;
    }

    @PostMapping
    public ResponseEntity<CreateSubscriptionResponse> createSubscription(@Validated @RequestBody CreateSubscriptionRequest req) {
        SubscriptionTier tier = SubscriptionTier.valueOf(req.subscriptionTier());
        SubscriptionDuration duration = SubscriptionDuration.valueOf(req.subscriptionDuration());
        Subscription subscription = subscriptionMapper.toModel(tier, duration);

        Subscription saved = subscriptionService.createSubscription(subscription);

        CreateSubscriptionResponse response = subscriptionMapper.toCreateSubscriptionResponse(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Utilizza {@link Optional} per rappresentare l’assenza della risorsa in modo esplicito,
     * evitando eccezioni e migliorando la leggibilità.
     * In un contesto REST restituisce 404 se la risorsa non esiste, senza sollevare errori.
     * Questo approccio è consigliato anche per le chiamate tra servizi interni.
     */

    @GetMapping("/{id}")
    public ResponseEntity<GetSubscriptionResponse> getSubscriptionById(@Validated @PathVariable Long id) {
        Optional<Subscription> subscriptionOptional = subscriptionService.getSubscriptionById(id);
        return subscriptionOptional
                .map(s -> ResponseEntity.ok(subscriptionMapper.toGetSubscriptionResponse(s)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Subscription>> getSubscriptionsBetweenDates(@Validated @RequestBody GetSubscriptionBetweenDatesRequest req) {
        LocalDate start = LocalDate.parse(req.start(), Utils.DATE_TIME_FORMATTER);
        LocalDate end = LocalDate.parse(req.end(), Utils.DATE_TIME_FORMATTER);
        Subscription subscription = subscriptionMapper.toModel(start, end);

        List<Subscription> subscriptions = subscriptionService.getSubscriptionBetweenDates(subscription.getStart(), subscription.getEnd());
        return ResponseEntity.ok(subscriptions);
    }
}

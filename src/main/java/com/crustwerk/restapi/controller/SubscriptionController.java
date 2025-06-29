package com.crustwerk.restapi.controller;

import com.crustwerk.restapi.mapper.SubscriptionMapper;
import com.crustwerk.restapi.service.SubscriptionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.crustwerk.restapi.controller.SubscriptionController.SUBSCRIPTION_ENDPOINT;


@RestController
@RequestMapping(SUBSCRIPTION_ENDPOINT)
@Validated
public class SubscriptionController {
    private final SubscriptionService SubscriptionService;
    private final SubscriptionMapper SubscriptionMapper;
    public static final String SUBSCRIPTION_ENDPOINT = "/api/subscriptions";

    public SubscriptionController(SubscriptionService SubscriptionService, SubscriptionMapper SubscriptionMapper) {
        this.SubscriptionService = SubscriptionService;
        this.SubscriptionMapper = SubscriptionMapper;
    }

}

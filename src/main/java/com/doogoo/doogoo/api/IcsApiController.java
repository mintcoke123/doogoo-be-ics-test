package com.doogoo.doogoo.api;

import com.doogoo.doogoo.dto.CreateIcsRequest;
import com.doogoo.doogoo.dto.CreateIcsResponse;
import com.doogoo.doogoo.subscription.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class IcsApiController {

    private final SubscriptionService subscriptionService;

    public IcsApiController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/ics")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateIcsResponse createIcs(@RequestBody CreateIcsRequest request) {
        return subscriptionService.createIcs(request);
    }
}

package com.doogoo.doogoo.subscription;

import com.doogoo.doogoo.dto.CreateIcsRequest;
import com.doogoo.doogoo.dto.CreateIcsResponse;
import com.doogoo.doogoo.subscription.domain.Subscription;
import com.doogoo.doogoo.subscription.repository.SubscriptionRepository;
import java.security.SecureRandom;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final String appBaseUrl;

    public SubscriptionService(
            SubscriptionRepository subscriptionRepository,
            @Value("${app.base-url:http://localhost:8080}") String appBaseUrl) {
        this.subscriptionRepository = subscriptionRepository;
        this.appBaseUrl = appBaseUrl.endsWith("/") ? appBaseUrl.substring(0, appBaseUrl.length() - 1) : appBaseUrl;
    }

    @Transactional
    public CreateIcsResponse createIcs(CreateIcsRequest request) {
        String token = generateToken();
        List<String> ids = request.selectedNoticed() != null ? request.selectedNoticed() : List.of();
        boolean alarmEnabled = Boolean.TRUE.equals(request.alarmEnabled());
        Integer alarmMin = request.alarmMinutesBefore();
        if (alarmMin != null && (alarmMin < 0 || alarmMin > 10080)) {
            alarmMin = null;
        }
        Subscription sub = Subscription.forSelectedNotices(token, ids, alarmEnabled, alarmMin);
        subscriptionRepository.save(sub);
        String base = appBaseUrl + "/cal/" + token + ".ics";
        String downloadUrl = base + "?download=true";
        return new CreateIcsResponse(token, base, downloadUrl);
    }

    private static final String TOKEN_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private String generateToken() {
        SecureRandom r = new SecureRandom();
        for (int i = 0; i < 10; i++) {
            StringBuilder sb = new StringBuilder(6);
            for (int j = 0; j < 6; j++) {
                sb.append(TOKEN_CHARS.charAt(r.nextInt(TOKEN_CHARS.length())));
            }
            String candidate = sb.toString();
            if (!subscriptionRepository.existsByToken(candidate)) {
                return candidate;
            }
        }
        throw new IllegalStateException("Failed to generate unique token");
    }
}

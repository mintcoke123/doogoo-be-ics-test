package com.doogoo.doogoo.ics;

import com.doogoo.doogoo.event.domain.Event;
import com.doogoo.doogoo.event.repository.EventRepository;
import com.doogoo.doogoo.subscription.domain.Subscription;
import com.doogoo.doogoo.subscription.repository.SubscriptionRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class IcsFeedService {

    private final SubscriptionRepository subscriptionRepository;
    private final EventRepository eventRepository;
    private final IcsService icsService;

    public IcsFeedService(
            SubscriptionRepository subscriptionRepository,
            EventRepository eventRepository,
            IcsService icsService) {
        this.subscriptionRepository = subscriptionRepository;
        this.eventRepository = eventRepository;
        this.icsService = icsService;
    }

    public Optional<String> getIcsByToken(String token) {
        return subscriptionRepository.findByToken(token)
                .map(sub -> {
                    List<Event> events = getEventsForSubscription(sub);
                    return icsService.toIcs(events, sub.getAlarmEnabled(), sub.getAlarmMinutesBefore());
                });
    }

    private List<Event> getEventsForSubscription(Subscription subscription) {
        List<String> noticeIds = subscription.getSelectedNoticeIds();
        if (noticeIds != null && !noticeIds.isEmpty()) {
            List<Event> list = eventRepository.findByExternalIdInOrderByStartTime(noticeIds);
            return list != null ? list : Collections.emptyList();
        }
        Instant from = Instant.now().minus(30, ChronoUnit.DAYS);
        List<Event> candidates = eventRepository.findByStartTimeGreaterThanEqualOrderByStartTime(from);
        String dept = subscription.getDepartment();
        List<String> keywords = subscription.getKeywords();
        return candidates.stream()
                .filter(e -> matchesDepartment(e, dept))
                .filter(e -> matchesKeywords(e, keywords))
                .collect(Collectors.toList());
    }

    private boolean matchesDepartment(Event e, String department) {
        if (department == null || department.isBlank()) return true;
        return department.equals(e.getDepartment()) || "전체".equals(e.getDepartment());
    }

    private boolean matchesKeywords(Event e, List<String> keywords) {
        if (keywords == null || keywords.isEmpty()) return true;
        String orig = e.getOriginalCategory();
        String title = e.getTitle();
        return keywords.stream().anyMatch(k ->
                (orig != null && orig.contains(k)) || (title != null && title.contains(k)));
    }
}

package com.doogoo.doogoo.config;

import com.doogoo.doogoo.event.domain.Event;
import com.doogoo.doogoo.event.domain.EventCategoryType;
import com.doogoo.doogoo.event.repository.EventRepository;
import com.doogoo.doogoo.subscription.domain.Subscription;
import com.doogoo.doogoo.subscription.repository.SubscriptionRepository;
import java.time.Instant;
import java.util.List;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class SeedDataRunner implements ApplicationRunner {

    private final SubscriptionRepository subscriptionRepository;
    private final EventRepository eventRepository;

    public SeedDataRunner(SubscriptionRepository subscriptionRepository,
                          EventRepository eventRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (subscriptionRepository.findByToken("Abcdef").isEmpty()) {
            Subscription sub = new Subscription("Abcdef", null, List.of(), List.of());
            subscriptionRepository.save(sub);
        }
        if (eventRepository.count() == 0) {
            saveDoDreamEvent("dd-2026-001-apply", "[신청] 동계 영화제작워크샵 : 초록제",
                    "2026-02-05T00:00:00Z", "2026-02-12T23:59:59Z", "영화예술학과",
                    "대상: 영화예술학과 / 신청 인원: 무제한", "예체능/워크샵", 100);
            saveDoDreamEvent("dd-2026-001-run", "[운영] 동계 영화제작워크샵 : 초록제",
                    "2026-02-13T10:00:00Z", "2026-02-13T18:00:00Z", "영화예술학과",
                    "실제 워크샵 운영 시간입니다.", "예체능/워크샵", 24);
            saveDoDreamEvent("dd-2026-002-apply", "[신청] 인융인의 재능봉사",
                    "2026-02-09T00:00:00Z", "2026-02-25T23:59:59Z", "인공지능융합대학",
                    "인공지능융합대학 소속 학생 재능기부 봉사활동 신청", "봉사/인공지능", 40);
            saveDoDreamEvent("dd-2026-003-apply", "[신청] 제9회 학정포럼",
                    "2026-02-19T00:00:00Z", "2026-03-05T23:59:59Z", "학술정보원",
                    "선착순 70명 마감이므로 빠른 신청 권장", "학술/포럼", 70);
            saveDoDreamEvent("dd-2026-004-run", "[운영] 기초학력증진프로그램(1차) : 파이썬",
                    "2026-04-01T00:00:00Z", "2026-06-28T23:59:59Z", "교수학습개발센터",
                    "한 학기 동안 진행되는 장기 운영 프로그램입니다.", "학습/IT", 55);
        }
    }

    private void saveDoDreamEvent(String externalId, String title, String startIso, String endIso,
                                  String location, String description, String originalCategory, int attendees) {
        Event e = new Event();
        e.setExternalId(externalId);
        e.setTitle(title);
        e.setCategory(mapCategory(originalCategory));
        e.setOriginalCategory(originalCategory);
        e.setStartTime(Instant.parse(startIso));
        e.setEndTime(Instant.parse(endIso));
        e.setLocation(location);
        e.setDepartment(location);
        e.setAttendees(attendees);
        e.setDescription(description != null && description.length() > 2000 ? description.substring(0, 2000) : description);
        eventRepository.save(e);
    }

    private static EventCategoryType mapCategory(String originalCategory) {
        if (originalCategory == null) return EventCategoryType.Academic;
        return switch (originalCategory) {
            case "예체능/워크샵", "학습/IT" -> EventCategoryType.Workshop;
            case "봉사/인공지능" -> EventCategoryType.Social;
            case "학술/포럼" -> EventCategoryType.Academic;
            default -> EventCategoryType.Academic;
        };
    }
}

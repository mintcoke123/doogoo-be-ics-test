package com.doogoo.doogoo.api;

import com.doogoo.doogoo.dto.NoticeItemDto;
import com.doogoo.doogoo.dto.NoticesResponse;
import com.doogoo.doogoo.event.domain.DoDreamConstants;
import com.doogoo.doogoo.event.domain.Event;
import com.doogoo.doogoo.event.repository.EventRepository;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NoticesController {

    private static final ZoneId SEOUL = ZoneId.of("Asia/Seoul");
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(SEOUL);

    private final EventRepository eventRepository;

    public NoticesController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping("/notices")
    public NoticesResponse getNotices() {
        List<Event> events = eventRepository.findAll().stream()
                .filter(e -> DoDreamConstants.isDoDream(e.getOriginalCategory()))
                .sorted((a, b) -> (a.getStartTime() != null && b.getStartTime() != null)
                        ? a.getStartTime().compareTo(b.getStartTime())
                        : 0)
                .toList();
        List<NoticeItemDto> notices = events.stream()
                .map(this::toNotice)
                .toList();
        return new NoticesResponse(notices);
    }

    private NoticeItemDto toNotice(Event e) {
        String applicationDate = e.getStartTime() != null ? DATE.format(e.getStartTime()) : "";
        String operatingDate = e.getEndTime() != null ? DATE.format(e.getEndTime()) : applicationDate;
        return new NoticeItemDto(
                e.getExternalId(),
                e.getTitle(),
                e.getDepartment(),
                applicationDate,
                operatingDate
        );
    }
}

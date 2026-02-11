package com.doogoo.doogoo.ics;

import com.doogoo.doogoo.event.domain.Event;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class IcsService {

    private static final ZoneId SEOUL = ZoneId.of("Asia/Seoul");
    private static final DateTimeFormatter DT_FORMAT =
            DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'").withZone(ZoneId.of("UTC"));

    public String toIcs(List<Event> events) {
        return toIcs(events, false, null);
    }

    public String toIcs(List<Event> events, Boolean alarmEnabled, Integer alarmMinutesBefore) {
        boolean addAlarm = Boolean.TRUE.equals(alarmEnabled) && alarmMinutesBefore != null && alarmMinutesBefore >= 0;
        StringBuilder sb = new StringBuilder();
        sb.append("BEGIN:VCALENDAR\r\n");
        sb.append("VERSION:2.0\r\n");
        sb.append("PRODID:-//Doogoo//Calendar//KO\r\n");
        sb.append("CALSCALE:GREGORIAN\r\n");

        for (Event e : events) {
            sb.append(toVEvent(e, addAlarm ? alarmMinutesBefore : null));
        }

        sb.append("END:VCALENDAR\r\n");
        return sb.toString();
    }

    private String toVEvent(Event e) {
        return toVEvent(e, null);
    }

    private String toVEvent(Event e, Integer alarmMinutesBefore) {
        String dtstamp = DT_FORMAT.format(Instant.now());
        String dtstart = e.getStartTime() != null ? DT_FORMAT.format(e.getStartTime()) : "";
        String dtend = e.getEndTime() != null ? DT_FORMAT.format(e.getEndTime()) : dtstart;

        String summary = escape(e.getTitle());
        String description = escape(e.getDescription());
        String location = escape(e.getLocation());

        StringBuilder sb = new StringBuilder();
        sb.append("BEGIN:VEVENT\r\n");
        sb.append("UID:").append(e.getExternalId() != null ? e.getExternalId() : "event-" + e.getId()).append("@doogoo\r\n");
        sb.append("DTSTAMP:").append(dtstamp).append("\r\n");
        sb.append("DTSTART:").append(dtstart).append("\r\n");
        sb.append("DTEND:").append(dtend).append("\r\n");
        sb.append("SUMMARY:").append(summary).append("\r\n");
        sb.append("DESCRIPTION:").append(description).append("\r\n");
        sb.append("LOCATION:").append(location).append("\r\n");
        if (alarmMinutesBefore != null && alarmMinutesBefore >= 0) {
            sb.append("BEGIN:VALARM\r\n");
            sb.append("TRIGGER:-PT").append(alarmMinutesBefore).append("M\r\n");
            sb.append("ACTION:DISPLAY\r\n");
            sb.append("DESCRIPTION:Reminder\r\n");
            sb.append("END:VALARM\r\n");
        }
        sb.append("END:VEVENT\r\n");
        return sb.toString();
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace(";", "\\;")
                .replace(",", "\\,")
                .replace("\r\n", "\\n")
                .replace("\n", "\\n");
    }
}

package com.doogoo.doogoo.dto;

import java.util.List;

public record CreateIcsRequest(
        List<String> selectedNoticed,
        Boolean alarmEnabled,
        Integer alarmMinutesBefore
) {
}

package com.doogoo.doogoo.dto;

public record NoticeItemDto(
        String noticeId,
        String title,
        String department,
        String applicationDate,
        String operatingDate
) {
}

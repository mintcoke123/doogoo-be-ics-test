package com.doogoo.doogoo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * GET /api/events 응답 내 이벤트 한 건 DTO.
 * API 명세: id, title, category, originalCategory, date, start, end, location, department, attendees, description, image
 */
@JsonInclude(Include.NON_NULL)
public record EventResponse(
        String id,
        String title,
        String category,
        String originalCategory,
        String date,
        String start,
        String end,
        String location,
        String department,
        Integer attendees,
        String description,
        String image
) {
}

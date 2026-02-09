package com.doogoo.doogoo.dto;

import java.util.List;

/**
 * GET /api/event-categories 응답 DTO.
 */
public record EventCategoriesResponse(
        List<String> categories
) {
}

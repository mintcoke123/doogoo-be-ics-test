package com.doogoo.doogoo.dto;

import java.util.List;

/**
 * GET /api/filter-config 응답 내 키워드 그룹.
 * API 명세: keywordGroups[].label, keywordGroups[].keywords
 */
public record KeywordGroupDto(
        String label,
        List<String> keywords
) {
}

package com.doogoo.doogoo.dto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * GET /api/events 쿼리 파라미터 바인딩용 DTO.
 * API 명세: department, years(콤마 구분), keywords(콤마 구분), from, to
 */
public record EventQueryRequest(
        String department,
        String years,
        String keywords,
        String from,
        String to
) {
    /** 콤마로 구분된 years 문자열을 리스트로 */
    public List<String> getYearsList() {
        if (years == null || years.isBlank()) {
            return Collections.emptyList();
        }
        return Arrays.stream(years.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }

    /** 콤마로 구분된 keywords 문자열을 리스트로 */
    public List<String> getKeywordsList() {
        if (keywords == null || keywords.isBlank()) {
            return Collections.emptyList();
        }
        return Arrays.stream(keywords.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }
}

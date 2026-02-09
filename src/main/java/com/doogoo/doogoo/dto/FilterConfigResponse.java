package com.doogoo.doogoo.dto;

import java.util.List;

/**
 * GET /api/filter-config 응답 DTO.
 */
public record FilterConfigResponse(
        List<CollegeDepartmentDto> departmentsByCollege,
        List<String> years,
        List<KeywordGroupDto> keywordGroups
) {
}

package com.doogoo.doogoo.dto;

import java.util.List;

public record FilterConfigResponse(
        List<CollegeDepartmentDto> departmentsByCollege,
        List<String> years,
        List<KeywordGroupDto> keywordGroups
) {
}

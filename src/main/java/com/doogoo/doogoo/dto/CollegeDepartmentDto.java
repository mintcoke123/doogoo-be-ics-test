package com.doogoo.doogoo.dto;

import java.util.List;

/**
 * GET /api/filter-config 응답 내 대학별 학과.
 * API 명세: departmentsByCollege[].college, departmentsByCollege[].departments
 */
public record CollegeDepartmentDto(
        String college,
        List<String> departments
) {
}

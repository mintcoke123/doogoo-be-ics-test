package com.doogoo.doogoo.dto;

/**
 * API 에러 응답 (명세 공통 사항).
 */
public record ErrorResponse(
        String error,
        String message
) {
}

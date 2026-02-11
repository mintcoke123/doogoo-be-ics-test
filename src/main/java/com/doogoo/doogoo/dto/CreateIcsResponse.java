package com.doogoo.doogoo.dto;

public record CreateIcsResponse(
        String token,
        String icsUrl,
        String downloadUrl
) {
}

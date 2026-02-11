package com.doogoo.doogoo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 서버 랜덤 포트 기동 + HTTP로 엔드포인트 호출하는 진짜 통합 테스트.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTest {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    /** 4xx/5xx 시 예외 없이 ResponseEntity 반환 (400/404 검증용) */
    private final RestTemplate restTemplateNoThrow = new RestTemplate() {{
        setRequestFactory(getRequestFactory());
        setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public boolean hasError(org.springframework.http.client.ClientHttpResponse response) {
                return false;
            }
        });
    }};

    @Test
    @DisplayName("통합: 유효한 token으로 ICS 피드 요청 시 200과 VCALENDAR 본문 반환")
    void ics_정상_통합_테스트() {
        String token = "Abcdef";
        String url = "http://localhost:" + port + "/cal/" + token + ".ics";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String body = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body).contains("BEGIN:VCALENDAR");
        assertThat(body).contains("BEGIN:VEVENT");
        assertThat(body).contains("END:VCALENDAR");
    }

    @Test
    @DisplayName("통합: download=true 시 Content-Disposition 헤더 포함")
    void ics_download_헤더_통합_테스트() {
        String token = "Abcdef";
        String url = "http://localhost:" + port + "/cal/" + token + ".ics?download=true";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertThat(response.getHeaders().getFirst("Content-Disposition")).isNotNull();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        String disposition = response.getHeaders().getFirst("Content-Disposition");
        assertThat(disposition).contains("attachment").contains("schedule.ics");
    }

    @Test
    @DisplayName("통합: 존재하지 않는 token 시 404")
    void ics_token_없음_통합_테스트() {
        String url = "http://localhost:" + port + "/cal/Abcdxx.ics";

        ResponseEntity<String> response = restTemplateNoThrow.getForEntity(url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}

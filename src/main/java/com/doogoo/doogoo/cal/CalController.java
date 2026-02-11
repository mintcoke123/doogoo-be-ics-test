package com.doogoo.doogoo.cal;

import com.doogoo.doogoo.ics.IcsFeedService;
import com.doogoo.doogoo.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalController {

    private final IcsFeedService icsFeedService;

    public CalController(IcsFeedService icsFeedService) {
        this.icsFeedService = icsFeedService;
    }

    @GetMapping(value = "/cal/{token}.ics", produces = "text/calendar")
    public ResponseEntity<String> getIcs(
            @PathVariable String token,
            @RequestParam(required = false, defaultValue = "false") boolean download) {

        String ics = icsFeedService.getIcsByToken(token)
                .orElseThrow(() -> new NotFoundException("Subscription not found"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/calendar; charset=UTF-8"));
        if (download) {
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"schedule.ics\"");
        }
        return ResponseEntity.ok().headers(headers).body(ics);
    }
}

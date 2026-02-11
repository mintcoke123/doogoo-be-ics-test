package com.doogoo.doogoo.api;

import com.doogoo.doogoo.dto.KeywordItemDto;
import com.doogoo.doogoo.dto.KeywordsResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class KeywordsController {

    @GetMapping("/keywords")
    public KeywordsResponse getKeywords() {
        List<KeywordItemDto> keywords = List.of(
                new KeywordItemDto("1", "해커톤"),
                new KeywordItemDto("2", "수강신청"),
                new KeywordItemDto("3", "학사일정")
        );
        return new KeywordsResponse(keywords);
    }
}

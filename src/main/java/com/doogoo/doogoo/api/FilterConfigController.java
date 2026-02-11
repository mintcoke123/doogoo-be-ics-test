package com.doogoo.doogoo.api;

import com.doogoo.doogoo.config.DefaultFilterConfig;
import com.doogoo.doogoo.dto.FilterConfigResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FilterConfigController {

    @GetMapping("/filter-config")
    public FilterConfigResponse getFilterConfig() {
        return DefaultFilterConfig.VALUE;
    }
}

package com.doogoo.doogoo.service;

import com.doogoo.doogoo.domain.Filter;
import com.doogoo.doogoo.domain.Notice;

import java.util.List;
import java.util.stream.Collectors;

public class FilterService {

    public List<Notice> apply(List<Notice> notices, Filter filter) {
        return notices.stream()
                .filter(notice -> matchesDepartment(notice, filter))
                .filter(notice -> matchesKeyword(notice, filter))
                .collect(Collectors.toList());
    }

    private boolean matchesDepartment(Notice notice, Filter filter) {
        if (filter.getDepartments().isEmpty()) {
            return true;
        }
        return filter.getDepartments().contains(notice.getDepartment());
    }

    private boolean matchesKeyword(Notice notice, Filter filter) {
        if (filter.getKeywords().isEmpty()) {
            return true;
        }
        return filter.getKeywords().stream()
                .anyMatch(keyword -> notice.getTitle().contains(keyword));
    }
}

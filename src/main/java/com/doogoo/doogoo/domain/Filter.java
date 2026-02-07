package com.doogoo.doogoo.domain;

import java.util.List;

public class Filter {

    private List<String> departments;
    private List<String> keywords;

    public Filter(List<String> departments, List<String> keywords) {
        this.departments = departments != null ? departments : List.of();
        this.keywords = keywords != null ? keywords : List.of();
    }

    public List<String> getDepartments() {
        return departments;
    }

    public List<String> getKeywords() {
        return keywords;
    }
}

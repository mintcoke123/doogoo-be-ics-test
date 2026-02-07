package com.doogoo.doogoo.domain;

public class Notice {

    private String title;
    private String department;

    public Notice(String title, String department) {
        this.title = title;
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public String getDepartment() {
        return department;
    }
}

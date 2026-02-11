package com.doogoo.doogoo.subscription.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "subscription")
public class Subscription {

    private static final String SEP = "|";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    private String token;

    @Column(length = 256)
    private String department;

    @Column(length = 512)
    private String yearsStorage;

    @Column(length = 1024)
    private String keywordsStorage;

    @Column(length = 2048)
    private String selectedNoticeIdsStorage;

    @Column(nullable = true)
    private Boolean alarmEnabled;

    @Column(nullable = true)
    private Integer alarmMinutesBefore;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    protected Subscription() {
    }

    public Subscription(String token, String department, List<String> years, List<String> keywords) {
        this.token = token;
        this.department = department == null || department.isBlank() ? null : department.trim();
        this.yearsStorage = years != null && !years.isEmpty()
                ? String.join(SEP, years)
                : "";
        this.keywordsStorage = keywords != null && !keywords.isEmpty()
                ? String.join(SEP, keywords)
                : "";
        this.selectedNoticeIdsStorage = null;
        this.alarmEnabled = null;
        this.alarmMinutesBefore = null;
    }

    public static Subscription forSelectedNotices(String token, List<String> selectedNoticeIds,
                                                  boolean alarmEnabled, Integer alarmMinutesBefore) {
        Subscription s = new Subscription(token, null, List.of(), List.of());
        s.setSelectedNoticeIds(selectedNoticeIds != null ? selectedNoticeIds : List.of());
        s.setAlarmEnabled(alarmEnabled);
        s.setAlarmMinutesBefore(alarmMinutesBefore != null && alarmMinutesBefore >= 0 && alarmMinutesBefore <= 10080
                ? alarmMinutesBefore : null);
        return s;
    }

    public List<String> getSelectedNoticeIds() {
        if (selectedNoticeIdsStorage == null || selectedNoticeIdsStorage.isBlank()) {
            return Collections.emptyList();
        }
        return Arrays.stream(selectedNoticeIdsStorage.split("\\" + SEP))
                .map(String::trim)
                .filter(x -> !x.isEmpty())
                .collect(Collectors.toList());
    }

    public void setSelectedNoticeIds(List<String> ids) {
        this.selectedNoticeIdsStorage = ids != null && !ids.isEmpty()
                ? String.join(SEP, ids)
                : null;
    }

    public Boolean getAlarmEnabled() {
        return alarmEnabled;
    }

    public void setAlarmEnabled(Boolean alarmEnabled) {
        this.alarmEnabled = alarmEnabled;
    }

    public Integer getAlarmMinutesBefore() {
        return alarmMinutesBefore;
    }

    public void setAlarmMinutesBefore(Integer alarmMinutesBefore) {
        this.alarmMinutesBefore = alarmMinutesBefore;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<String> getYears() {
        if (yearsStorage == null || yearsStorage.isBlank()) {
            return Collections.emptyList();
        }
        return Arrays.stream(yearsStorage.split("\\" + SEP))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    public void setYears(List<String> years) {
        this.yearsStorage = years != null && !years.isEmpty()
                ? String.join(SEP, years)
                : "";
    }

    public List<String> getKeywords() {
        if (keywordsStorage == null || keywordsStorage.isBlank()) {
            return Collections.emptyList();
        }
        return Arrays.stream(keywordsStorage.split("\\" + SEP))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    public void setKeywords(List<String> keywords) {
        this.keywordsStorage = keywords != null && !keywords.isEmpty()
                ? String.join(SEP, keywords)
                : "";
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}

package com.doogoo.doogoo.event.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 공지/이벤트 엔티티 (학사 일정, 두드림 등).
 * API 명세: GET /api/events 응답 필드와 매핑.
 */
@Entity
@Table(name = "notice_event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** API 응답용 외부 식별자 (예: 2026-02-01, dd-2026-001-apply) */
    @Column(nullable = false, unique = true, length = 64)
    private String externalId;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private EventCategoryType category;

    /** 원본 한글 카테고리 (예: 성적/시험, 예체능/워크샵) */
    @Column(length = 64)
    private String originalCategory;

    @Column(nullable = false)
    private Instant startTime;

    private Instant endTime;

    @Column(nullable = false)
    private String location;

    /** 대상 학과/부서. "전체" 가능 */
    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private int attendees = 0;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(length = 512)
    private String imageUrl;

    protected Event() {
    }

    public Long getId() {
        return id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public EventCategoryType getCategory() {
        return category;
    }

    public void setCategory(EventCategoryType category) {
        this.category = category;
    }

    public String getOriginalCategory() {
        return originalCategory;
    }

    public void setOriginalCategory(String originalCategory) {
        this.originalCategory = originalCategory;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getAttendees() {
        return attendees;
    }

    public void setAttendees(int attendees) {
        this.attendees = attendees;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /** YYYY-MM-DD 형식 날짜 (API date 필드) */
    public String getDate() {
        return startTime.atZone(ZoneId.of("Asia/Seoul"))
                .format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}

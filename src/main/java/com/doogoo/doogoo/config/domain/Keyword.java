package com.doogoo.doogoo.config.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * 키워드 엔티티 (그룹 소속).
 * API 명세: GET /api/filter-config → keywordGroups[].keywords[]
 */
@Entity
@Table(name = "config_keyword")
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int displayOrder = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_group_id", nullable = false)
    private KeywordGroup keywordGroup;

    protected Keyword() {
    }

    public Keyword(String name, KeywordGroup keywordGroup, int displayOrder) {
        this.name = name;
        this.keywordGroup = keywordGroup;
        this.displayOrder = displayOrder;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public KeywordGroup getKeywordGroup() {
        return keywordGroup;
    }

    public void setKeywordGroup(KeywordGroup keywordGroup) {
        this.keywordGroup = keywordGroup;
    }
}

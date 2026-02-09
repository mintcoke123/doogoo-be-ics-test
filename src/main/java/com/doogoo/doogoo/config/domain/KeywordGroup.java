package com.doogoo.doogoo.config.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;

/**
 * 키워드 그룹 엔티티 (예: 학사공지, 두드림).
 * API 명세: GET /api/filter-config → keywordGroups[].label
 */
@Entity
public class KeywordGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    private int displayOrder = 0;

    @OneToMany(mappedBy = "keywordGroup")
    @OrderBy("displayOrder, name")
    private List<Keyword> keywords = new ArrayList<>();

    protected KeywordGroup() {
    }

    public KeywordGroup(String label, int displayOrder) {
        this.label = label;
        this.displayOrder = displayOrder;
    }

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }
}

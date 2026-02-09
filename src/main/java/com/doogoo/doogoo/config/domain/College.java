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
 * 대학 엔티티.
 * API 명세: GET /api/filter-config → departmentsByCollege[].college
 */
@Entity
public class College {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int displayOrder = 0;

    @OneToMany(mappedBy = "college")
    @OrderBy("displayOrder, name")
    private List<Department> departments = new ArrayList<>();

    protected College() {
    }

    public College(String name, int displayOrder) {
        this.name = name;
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

    public List<Department> getDepartments() {
        return departments;
    }
}

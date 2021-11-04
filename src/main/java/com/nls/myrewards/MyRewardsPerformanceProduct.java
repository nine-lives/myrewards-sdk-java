package com.nls.myrewards;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class MyRewardsPerformanceProduct {
    public static final TypeReference<List<MyRewardsPerformanceProduct>> LIST_TYPE_REFERENCE = new TypeReference<List<MyRewardsPerformanceProduct>>() { };

    private Integer id;
    private int peformanceCategoryId;
    private String name;
    private String ref;
    private String productType;
    private Integer value;
    private String description;

    public Integer getId() {
        return id;
    }

    public int getPerformanceCategoryId() {
        return peformanceCategoryId;
    }

    public String getName() {
        return name;
    }

    public String getRef() {
        return ref;
    }

    public String getProductType() {
        return productType;
    }

    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}

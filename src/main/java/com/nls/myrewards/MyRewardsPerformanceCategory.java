package com.nls.myrewards;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class MyRewardsPerformanceCategory {
    public static final TypeReference<List<MyRewardsPerformanceCategory>> LIST_TYPE_REFERENCE = new TypeReference<List<MyRewardsPerformanceCategory>>() { };

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

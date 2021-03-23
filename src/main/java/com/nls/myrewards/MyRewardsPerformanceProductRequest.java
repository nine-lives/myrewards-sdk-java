package com.nls.myrewards;

import java.util.List;

public class MyRewardsPerformanceProductRequest {
    private Integer id;
    private int performanceCategoryId;
    private String name;
    private String ref;
    private String productType;
    private Integer value;
    private String description;

    public Integer getId() {
        return id;
    }

    public MyRewardsPerformanceProductRequest withId(Integer id) {
        this.id = id;
        return this;
    }

    public int getPerformanceCategoryId() {
        return performanceCategoryId;
    }

    public MyRewardsPerformanceProductRequest withPerformanceCategoryId(int performanceCategoryId) {
        this.performanceCategoryId = performanceCategoryId;
        return this;
    }

    public String getName() {
        return name;
    }

    public MyRewardsPerformanceProductRequest withName(String name) {
        this.name = name;
        return this;
    }

    public String getRef() {
        return ref;
    }

    public MyRewardsPerformanceProductRequest withRef(String ref) {
        this.ref = ref;
        return this;
    }

    public String getProductType() {
        return productType;
    }

    public MyRewardsPerformanceProductRequest withProductType(String productType) {
        this.productType = productType;
        return this;
    }

    public Integer getValue() {
        return value;
    }

    public MyRewardsPerformanceProductRequest withValue(Integer value) {
        this.value = value;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public MyRewardsPerformanceProductRequest withDescription(String description) {
        this.description = description;
        return this;
    }

    public static class ListWrapper {
        private List<MyRewardsPerformanceProductRequest> performanceProducts;

        ListWrapper() {
        }

        ListWrapper(List<MyRewardsPerformanceProductRequest> performanceProducts) {
            this.performanceProducts = performanceProducts;
        }

        public List<MyRewardsPerformanceProductRequest> getPerformanceProducts() {
            return performanceProducts;
        }
    }

}

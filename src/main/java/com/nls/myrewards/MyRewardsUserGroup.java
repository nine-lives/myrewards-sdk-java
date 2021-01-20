package com.nls.myrewards;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MyRewardsUserGroup {
    private int id;
    private String name;
    private Integer parentId;
    @JsonProperty("default")
    private boolean defaultGroup;
    private int position;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public boolean isDefaultGroup() {
        return defaultGroup;
    }

    public int getPosition() {
        return position;
    }
}

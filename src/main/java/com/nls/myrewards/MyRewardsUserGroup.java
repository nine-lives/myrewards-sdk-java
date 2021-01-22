package com.nls.myrewards;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class MyRewardsUserGroup {
    public static final TypeReference<List<MyRewardsUserGroup>> LIST_TYPE_REFERENCE = new TypeReference<List<MyRewardsUserGroup>>() { };

    private int id;
    private String name;
    private String programme; // undocumented
    private Integer parentId;
    @JsonProperty("default")
    private boolean defaultGroup;
    private int position;
    private String imageUrl;

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

    public String getProgramme() {
        return programme;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

package com.nls.myrewards;

public class MyRewardsUserGroupRequest {
    private String name;
    private int position;
    private Integer parentId;
    private String imageUrl;

    public String getName() {
        return name;
    }

    public MyRewardsUserGroupRequest withName(String name) {
        this.name = name;
        return this;
    }

    public int getPosition() {
        return position;
    }

    public MyRewardsUserGroupRequest withPosition(int position) {
        this.position = position;
        return this;
    }

    public Integer getParentId() {
        return parentId;
    }

    public MyRewardsUserGroupRequest withParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public MyRewardsUserGroupRequest withImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}

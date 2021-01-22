package com.nls.myrewards;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class MyRewardsPermission implements IMyRewardsPermission {
    public static final TypeReference<List<MyRewardsPermission>> LIST_TYPE_REFERENCE = new TypeReference<List<MyRewardsPermission>>() { };

    private int id;
    private String name;
    private String hint;
    private String resourceGroupName;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHint() {
        return hint;
    }

    public String getResourceGroupName() {
        return resourceGroupName;
    }

    @Override
    public String getPermissionGroupName() {
        return getResourceGroupName();
    }
}

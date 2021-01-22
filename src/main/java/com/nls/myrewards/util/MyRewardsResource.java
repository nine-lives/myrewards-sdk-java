package com.nls.myrewards.util;

import com.nls.myrewards.MyRewardsPermission;

import java.util.List;

public class MyRewardsResource {
    private final String name;
    private final List<MyRewardsPermission> permissions;

    public MyRewardsResource(String name, List<MyRewardsPermission> permissions) {
        this.name = name;
        this.permissions = permissions;
    }

    public String getName() {
        return name;
    }

    public List<MyRewardsPermission> getPermissions() {
        return permissions;
    }

    public boolean hasPermission(String name) {
        return permissions.stream().anyMatch(o -> o.getName().equals(name));
    }

    public MyRewardsPermission getPermission(String name) {
        return permissions.stream().filter(o -> o.getName().equals(name)).findAny().orElse(null);
    }
}

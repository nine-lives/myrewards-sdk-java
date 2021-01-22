package com.nls.myrewards.util;

import com.nls.myrewards.MyRewardsUserGroupPermission;

import java.util.List;
import java.util.stream.Collectors;

public class MyRewardsUserGroupResource {
    private final String name;
    private final List<MyRewardsUserGroupPermission> permissions;

    public MyRewardsUserGroupResource(String name, List<MyRewardsUserGroupPermission> permissions) {
        this.name = name;
        this.permissions = permissions;
    }

    public String getName() {
        return name;
    }

    public List<MyRewardsUserGroupPermission> getPermissions() {
        return permissions;
    }

    public List<MyRewardsUserGroupPermission> getActivePermissions() {
        return permissions.stream().filter(MyRewardsUserGroupPermission::isActive).collect(Collectors.toList());
    }

    public boolean hasPermission(String name) {
        return permissions.stream().anyMatch(o -> o.getName().equals(name));
    }

    public boolean hasActivePermission(String name) {
        return permissions.stream().filter(o -> o.getName().equals(name)).findAny()
                .map(MyRewardsUserGroupPermission::isActive)
                .orElse(false);
    }

    public MyRewardsUserGroupPermission getPermission(String name) {
        return permissions.stream().filter(o -> o.getName().equals(name)).findAny().orElse(null);
    }
}

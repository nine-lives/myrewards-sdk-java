package com.nls.myrewards.util;

import com.nls.myrewards.IMyRewardsPermission;

import java.util.Collections;
import java.util.List;

public class MyRewardsPermissionGroup<T extends IMyRewardsPermission> {
    private final String name;
    private final List<T> permissions;

    public MyRewardsPermissionGroup(String name, List<T> permissions) {
        this.name = name;
        this.permissions = Collections.unmodifiableList(permissions);
    }

    public String getName() {
        return name;
    }

    public List<T> getPermissions() {
        return permissions;
    }

    public boolean hasPermission(String name) {
        return permissions.stream().anyMatch(o -> o.getName().equals(name));
    }

    public T getPermission(String name) {
        return permissions.stream().filter(o -> o.getName().equals(name)).findAny().orElse(null);
    }
}

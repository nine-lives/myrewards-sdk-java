package com.nls.myrewards.util;

import com.nls.myrewards.IMyRewardsStatefulPermission;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MyRewardsStatefulPermissionGroup<T extends IMyRewardsStatefulPermission> extends MyRewardsPermissionGroup<T> {
    public MyRewardsStatefulPermissionGroup(String name, List<T> permissions) {
        super(name, permissions);
    }

    public List<T> getActivePermissions() {
        return getPermissions().stream().filter(T::isActive).collect(Collectors.toList());
    }

    public boolean hasActivePermission(String name) {
        return Optional.of(getPermission(name)).map(T::isActive).orElse(false);
    }
}

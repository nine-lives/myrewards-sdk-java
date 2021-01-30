package com.nls.myrewards.util;

import com.nls.myrewards.IMyRewardsPermission;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class MyRewardsPermissionGroups<T extends IMyRewardsPermission> {
    private final List<T> permissions;
    private final Map<String, MyRewardsPermissionGroup<T>> resources;

    public MyRewardsPermissionGroups(List<T> permissions) {
        Map<String, List<T>> grouping = permissions.stream().collect(Collectors.groupingBy(T::getPermissionGroupName));
        Map<String, MyRewardsPermissionGroup<T>> resources = new TreeMap<>();
        for (Map.Entry<String, List<T>> entry : grouping.entrySet()) {
            resources.put(entry.getKey(), new MyRewardsPermissionGroup<>(entry.getKey(), entry.getValue()));
        }
        this.resources = Collections.unmodifiableMap(resources);
        this.permissions = Collections.unmodifiableList(permissions);
    }

    public List<T> getPermissions() {
        return permissions;
    }

    public Set<String> getGroupNames() {
        return new TreeSet<>(resources.keySet());
    }

    public MyRewardsPermissionGroup<T> getGroup(String name) {
        return resources.get(name);
    }
}

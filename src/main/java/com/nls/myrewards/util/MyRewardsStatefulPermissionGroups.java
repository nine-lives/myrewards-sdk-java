package com.nls.myrewards.util;

import com.nls.myrewards.IMyRewardsStatefulPermission;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class MyRewardsStatefulPermissionGroups<T extends IMyRewardsStatefulPermission> {
    private final List<T> permissions;
    private final Map<String, MyRewardsStatefulPermissionGroup<T>> resources;

    public MyRewardsStatefulPermissionGroups(List<T> permissions) {
        Map<String, List<T>> grouping = permissions.stream().collect(Collectors.groupingBy(T::getPermissionGroupName));
        Map<String, MyRewardsStatefulPermissionGroup<T>> resources = new TreeMap<>();
        for (Map.Entry<String, List<T>> entry : grouping.entrySet()) {
            resources.put(entry.getKey(), new MyRewardsStatefulPermissionGroup<>(entry.getKey(), entry.getValue()));
        }
        this.resources = Collections.unmodifiableMap(resources);
        this.permissions = Collections.unmodifiableList(permissions);    }


    public List<T> getPermissions() {
        return permissions;
    }

    public List<T> getActivePermissions() {
        return getPermissions().stream().filter(T::isActive).collect(Collectors.toList());
    }

    public Set<String> getGroupNames() {
        return new TreeSet<>(resources.keySet());
    }

    public Set<String> getActiveGroupNames() {
        return resources.entrySet().stream()
                .filter(o -> !o.getValue().getActivePermissions().isEmpty())
                .map(Map.Entry::getKey).collect(Collectors.toCollection(TreeSet::new));
    }

    public MyRewardsStatefulPermissionGroup<T> getGroup(String name) {
        return resources.get(name);
    }

    public static <T extends IMyRewardsStatefulPermission> MyRewardsStatefulPermissionGroups<T> make(List<T> permissions) {
        return new MyRewardsStatefulPermissionGroups<T>(permissions);
    }
}

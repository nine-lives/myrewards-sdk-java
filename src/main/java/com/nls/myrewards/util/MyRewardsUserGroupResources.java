package com.nls.myrewards.util;

import com.nls.myrewards.MyRewardsUserGroup;
import com.nls.myrewards.MyRewardsUserGroupPermission;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MyRewardsUserGroupResources {
    private final MyRewardsUserGroup group;
    private final Map<String, MyRewardsUserGroupResource> resources;

    public MyRewardsUserGroupResources(MyRewardsUserGroup group, List<MyRewardsUserGroupPermission> permissions) {
        this.group = group;
        this.resources = Collections.unmodifiableMap(permissions.stream()
                .collect(Collectors.groupingBy(MyRewardsUserGroupPermission::getPermissionGroupName))
                .entrySet().stream()
                .map(e -> new MyRewardsUserGroupResource(e.getKey(), e.getValue()))
                .collect(Collectors.toMap(MyRewardsUserGroupResource::getName, Function.identity())));
    }

    public MyRewardsUserGroup getGroup() {
        return group;
    }

    public Set<String> getResourceNames() {
        return resources.keySet();
    }

    public Set<String> getActiveResourceNames() {
        return resources.entrySet().stream()
                .filter(o -> !o.getValue().getActivePermissions().isEmpty())
                .map(Map.Entry::getKey).collect(Collectors.toSet());
    }

    public MyRewardsUserGroupResource getResource(String name) {
        return resources.get(name);
    }
}

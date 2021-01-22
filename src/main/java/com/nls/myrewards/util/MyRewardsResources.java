package com.nls.myrewards.util;

import com.nls.myrewards.MyRewardsPermission;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MyRewardsResources {
    private final Map<String, MyRewardsResource> resources;

    public MyRewardsResources(List<MyRewardsPermission> permissions) {
        resources = Collections.unmodifiableMap(permissions.stream()
                .collect(Collectors.groupingBy(MyRewardsPermission::getResourceGroupName))
                .entrySet().stream()
                .map(e -> new MyRewardsResource(e.getKey(), e.getValue()))
                .collect(Collectors.toMap(MyRewardsResource::getName, Function.identity())));
    }


    public Set<String> getResourceNames() {
        return resources.keySet();
    }

    public MyRewardsResource getResource(String name) {
        return resources.get(name);
    }
}

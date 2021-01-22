package com.nls.myrewards;

import java.util.List;

public class MyRewardsUserGroupPermission implements IMyRewardsPermission {
    private int id;
    private String name;
    private String permissionGroupName;
    private boolean active;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPermissionGroupName() {
        return permissionGroupName;
    }

    public boolean isActive() {
        return active;
    }

    public MyRewardsUserGroupPermission withActive(boolean active) {
        this.active = active;
        return this;
    }

    public static class ListWrapper {
        private List<MyRewardsUserGroupPermission> permissions;

        ListWrapper() {
        }

        ListWrapper(List<MyRewardsUserGroupPermission> permissions) {
            this.permissions = permissions;
        }

        public List<MyRewardsUserGroupPermission> getPermissions() {
            return permissions;
        }
    }
}

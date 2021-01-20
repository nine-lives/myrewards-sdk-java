package com.nls.myrewards;

import java.util.List;

public class MyRewardsUserGroupPermission {
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

    static class ListWrapper {
        private List<MyRewardsUserGroupPermission> permissions;

        public ListWrapper() {
        }

        public ListWrapper(List<MyRewardsUserGroupPermission> permissions) {
            this.permissions = permissions;
        }

        public List<MyRewardsUserGroupPermission> getPermissions() {
            return permissions;
        }
    }
}

package com.nls.myrewards;

import java.util.List;

public class MyRewardsUserPermission extends MyRewardsUserGroupPermission {
    private MyRewardsPermissionState state;

    public MyRewardsPermissionState getState() {
        return state;
    }

    public static class ListWrapper {
        private List<MyRewardsUserPermission> permissions;

        ListWrapper() {
        }

        ListWrapper(List<MyRewardsUserPermission> permissions) {
            this.permissions = permissions;
        }

        public List<MyRewardsUserPermission> getPermissions() {
            return permissions;
        }
    }
}

package com.nls.myrewards.util;

import com.nls.myrewards.MyRewardsUserGroup;

import java.util.List;

public class MyRewardsUserGroupTree {
    private final MyRewardsUserGroupNode root;

    public MyRewardsUserGroupTree(List<MyRewardsUserGroup> groups) {
        this.root = new MyRewardsUserGroupNode(null, groups);
    }

    public List<MyRewardsUserGroupNode> getChildren() {
        return root.getChildren();
    }

    public MyRewardsUserGroupNode find(int groupId) {
        return root.find(groupId);
    }

    public List<MyRewardsUserGroupNode> list() {
        return root.list();
    }
}

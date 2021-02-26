package com.nls.myrewards.util;

import com.nls.myrewards.MyRewardsUserGroup;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MyRewardsUserGroupNode extends MyRewardsUserGroup {
    private final MyRewardsUserGroup group;
    private final List<MyRewardsUserGroupNode> children;


    MyRewardsUserGroupNode(MyRewardsUserGroup group, List<MyRewardsUserGroup> groups) {
        this.group = group;
        this.children = groups.stream()
                .filter(this::filter)
                .map(o -> new MyRewardsUserGroupNode(o, groups))
                .sorted(Comparator.comparing(MyRewardsUserGroupNode::getPosition).thenComparing(MyRewardsUserGroupNode::getId))
                .collect(Collectors.toList());
    }

    private boolean filter(MyRewardsUserGroup child) {
        if (child.getParentId() == null) {
            return this.group == null;
        }

        return this.group != null && child.getParentId().equals(this.group.getId());
    }

    public List<MyRewardsUserGroupNode> getChildren() {
        return children;
    }

    public MyRewardsUserGroupNode find(int groupId) {
        if (group != null && groupId == group.getId()) {
            return this;
        }

        for (MyRewardsUserGroupNode child : children) {
            MyRewardsUserGroupNode node = child.find(groupId);
            if (node != null) {
                return node;
            }
        }

        return null;
    }

    public List<MyRewardsUserGroupNode> list() {
        return list(this, new ArrayList<>());
    }

    private List<MyRewardsUserGroupNode> list(MyRewardsUserGroupNode node, List<MyRewardsUserGroupNode> result) {
        if (node.group != null) {
            result.add(node);
        }

        for (MyRewardsUserGroupNode child : node.getChildren()) {
            list(child, result);
        }

        return result;
    }

    @Override
    public int getId() {
        return group.getId();
    }

    @Override
    public String getName() {
        return group.getName();
    }

    @Override
    public Integer getParentId() {
        return group.getParentId();
    }

    @Override
    public boolean isDefaultGroup() {
        return group.isDefaultGroup();
    }

    @Override
    public int getPosition() {
        return group.getPosition();
    }

    @Override
    public String getProgramme() {
        return group.getProgramme();
    }

    @Override
    public String getImageUrl() {
        return group.getImageUrl();
    }
}

package com.nls.myrewards;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class MyRewardsDataWidget {
    public static final TypeReference<List<MyRewardsDataWidget>> LIST_TYPE_REFERENCE = new TypeReference<List<MyRewardsDataWidget>>() { };

    private int id;
    private String name;
    private MyRewardsUserDataWidget userDataWidget;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MyRewardsUserDataWidget getUserDataWidget() {
        return userDataWidget;
    }
}

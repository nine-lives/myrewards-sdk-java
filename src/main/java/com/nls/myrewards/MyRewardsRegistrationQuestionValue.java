package com.nls.myrewards;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class MyRewardsRegistrationQuestionValue {
    public static final TypeReference<List<MyRewardsRegistrationQuestionValue>> LIST_TYPE_REFERENCE = new TypeReference<List<MyRewardsRegistrationQuestionValue>>() { };

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

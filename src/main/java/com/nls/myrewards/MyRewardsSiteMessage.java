package com.nls.myrewards;

import com.fasterxml.jackson.core.type.TypeReference;
import org.joda.time.DateTime;

import java.util.List;

public class MyRewardsSiteMessage {
    public static final TypeReference<List<MyRewardsSiteMessage>> LIST_TYPE_REFERENCE = new TypeReference<List<MyRewardsSiteMessage>>() { };

    private int id;
    private int userId;
    private String content;
    private DateTime createdAt;

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }
}

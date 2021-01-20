package com.nls.myrewards;

import org.joda.time.DateTime;

public class MyRewardsSiteMessage {
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

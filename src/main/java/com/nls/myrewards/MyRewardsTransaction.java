package com.nls.myrewards;

import org.joda.time.DateTime;

public class MyRewardsTransaction {
    private int id;
    private int userId;
    private String description;
    private int value;
    private int balance;
    private String transactionType;
    private Integer remoteTransactionId;
    private DateTime createdAt;

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }

    public int getBalance() {
        return balance;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public Integer getRemoteTransactionId() {
        return remoteTransactionId;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }
}

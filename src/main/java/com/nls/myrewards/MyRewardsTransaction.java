package com.nls.myrewards;

import com.fasterxml.jackson.core.type.TypeReference;
import org.joda.time.DateTime;

import java.util.List;

public class MyRewardsTransaction {
    public static final TypeReference<List<MyRewardsTransaction>> LIST_TYPE_REFERENCE = new TypeReference<List<MyRewardsTransaction>>() { };

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

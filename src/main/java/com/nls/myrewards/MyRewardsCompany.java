package com.nls.myrewards;

import com.fasterxml.jackson.core.type.TypeReference;
import org.joda.time.DateTime;

import java.util.List;

public class MyRewardsCompany {
    public static final TypeReference<List<MyRewardsCompany>> LIST_TYPE_REFERENCE = new TypeReference<List<MyRewardsCompany>>() { };

    private int id;
    private String name;
    private String identifier;
    private boolean disabled;
    private EarningType earningType;
    private DateTime createdAt;
    private DateTime updatedAt;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public EarningType getEarningType() {
        return earningType;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }
}

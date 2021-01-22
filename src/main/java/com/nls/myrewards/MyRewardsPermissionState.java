package com.nls.myrewards;

public enum MyRewardsPermissionState {
    SameAsUserGroup("Same As User Group"),
    AlwaysAllow("Always Allow"),
    AlwaysDeny("Always Deny");

    private final String value;

    MyRewardsPermissionState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static MyRewardsPermissionState fromValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        for (MyRewardsPermissionState state : values()) {
            if (state.value.equals(value)) {
                return state;
            }
        }

        throw new IllegalArgumentException("Value not found for " + value);
    }
}

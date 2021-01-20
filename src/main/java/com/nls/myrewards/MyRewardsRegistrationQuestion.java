package com.nls.myrewards;

public final class MyRewardsRegistrationQuestion {
    private int id;
    private String label;
    private boolean mandatory;
    private String fieldType;
    private String options;
    private String fieldName;
    private boolean freeText;

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public String getFieldType() {
        return fieldType;
    }

    public String getOptions() {
        return options;
    }

    public String getFieldName() {
        return fieldName;
    }

    public boolean isFreeText() {
        return freeText;
    }
}

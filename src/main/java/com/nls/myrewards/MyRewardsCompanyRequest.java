package com.nls.myrewards;

public class MyRewardsCompanyRequest {

    private String name;
    private String identifier;
    private String disabled;
    private EarningType earningType;

    public MyRewardsCompanyRequest() {
    }

    public MyRewardsCompanyRequest(MyRewardsCompany company) {
        this.name = company.getName();
        this.identifier = company.getIdentifier();
        withDisabled(company.isDisabled());
        this.earningType = company.getEarningType();
    }

    public String getName() {
        return name;
    }

    public MyRewardsCompanyRequest withName(String name) {
        this.name = name;
        return this;
    }

    public String getIdentifier() {
        return identifier;
    }

    public MyRewardsCompanyRequest withIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public boolean getDisabled() {
        return "Y".equals(disabled);
    }

    public MyRewardsCompanyRequest withDisabled(boolean disabled) {
        this.disabled = disabled ? "Y" : "N";
        return this;
    }

    public EarningType getEarningType() {
        return earningType;
    }

    public MyRewardsCompanyRequest withEarningType(EarningType earningType) {
        this.earningType = earningType;
        return this;
    }
}

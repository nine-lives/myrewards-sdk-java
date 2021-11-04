package com.nls.myrewards;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyRewardsUser {
    public enum CompanyType {
        FreeText,
        ManagedList
    }

    private int id;
    private String username;
    private String email;
    private String title;
    private String firstname;
    private String lastname;
    private Object company;
    private String jobTitle;
    @JsonProperty("address_1")
    private String address1;
    @JsonProperty("address_2")
    private String address2;
    private String town;
    private String postcode;
    private String county;
    private String country;
    private LocalDate dateOfBirth;
    private String telephone;
    private String mobile;
    private String chosenLocale;
    private boolean tsandcs;
    private Integer userGroupId;
    private boolean consented;
    private boolean marketingConsented;
    private List<MyRewardsRegistrationAnswerAttribute> registrationAnswersAttributes = new ArrayList<>();

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public CompanyType getCompanyType() {
        return company instanceof String ? CompanyType.FreeText : CompanyType.ManagedList;
    }

    public String getCompany() {
        return getCompanyType() == CompanyType.FreeText
                ? company.toString()
                : ((Map<String, String>) company).get("name");
    }

    public String getCompanyIdentifier() {
        return getCompanyType() == CompanyType.FreeText
                ? null
                : ((Map<String, String>) company).get("identifier");
    }

    public Integer getCompanyId() {
        return getCompanyType() == CompanyType.FreeText
                ? null
                : ((Map<String, Integer>) company).get("id");
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getTown() {
        return town;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCounty() {
        return county;
    }

    public String getCountry() {
        return country;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public String getChosenLocale() {
        return chosenLocale;
    }

    public boolean isTsandcs() {
        return tsandcs;
    }

    public Integer getUserGroupId() {
        return userGroupId;
    }

    public boolean isConsented() {
        return consented;
    }

    public boolean isMarketingConsented() {
        return marketingConsented;
    }

    public List<MyRewardsRegistrationAnswerAttribute> getRegistrationAnswersAttributes() {
        return registrationAnswersAttributes;
    }
}

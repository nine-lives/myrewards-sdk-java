package com.nls.myrewards;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MyRewardsUserRequest {
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
    private Boolean tsandcs;
    private Integer userGroupId;
    private Boolean consented;
    private Boolean marketingConsented;
    private List<MyRewardsRegistrationAnswerAttribute> registrationAnswersAttributes = new ArrayList<>();

    public MyRewardsUserRequest() {
    }

    public MyRewardsUserRequest(MyRewardsUser user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.title = user.getTitle();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        if (user.getCompanyType() == MyRewardsUser.CompanyType.ManagedList) {
            withCompany(user.getCompany(), user.getCompanyIdentifier());
        } else {
            withCompany(user.getCompany());
        }
        this.jobTitle = user.getJobTitle();
        this.address1 = user.getAddress1();
        this.address2 = user.getAddress2();
        this.town = user.getTown();
        this.postcode = user.getPostcode();
        this.county = user.getCounty();
        this.country = user.getCountry();
        this.dateOfBirth = user.getDateOfBirth();
        this.telephone = user.getTelephone();
        this.mobile = user.getMobile();
        this.chosenLocale = user.getChosenLocale();
        this.tsandcs = user.isTsandcs();
        this.userGroupId = user.getUserGroupId();
        this.consented = user.isConsented();
        this.marketingConsented = user.isMarketingConsented();
        this.registrationAnswersAttributes = user.getRegistrationAnswersAttributes() == null
                ? null
                : user.getRegistrationAnswersAttributes().stream()
                    .map(MyRewardsRegistrationAnswerAttribute::new)
                    .collect(Collectors.toList());
    }

    public String getUsername() {
        return username;
    }

    public MyRewardsUserRequest withUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public MyRewardsUserRequest withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MyRewardsUserRequest withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public MyRewardsUserRequest withFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public MyRewardsUserRequest withLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public String getCompany() {
        return company instanceof Map
                ? ((Map<String, String>) company).get("name")
                : (String) company;
    }

    public String getCompanyIdentifier() {
        return company instanceof Map
                ? ((Map<String, String>) company).get("identifier")
                : null;
    }

    public MyRewardsUserRequest withCompany(String company) {
        this.company = company;
        return this;
    }

    public MyRewardsUserRequest withCompany(String company, String identifier) {
        Map<String, String> map = new HashMap<>();
        map.put("name", company);
        map.put("identifier", identifier);
        this.company = map;
        return this;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public MyRewardsUserRequest withJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    public String getAddress1() {
        return address1;
    }

    public MyRewardsUserRequest withAddress1(String address1) {
        this.address1 = address1;
        return this;
    }

    public String getAddress2() {
        return address2;
    }

    public MyRewardsUserRequest withAddress2(String address2) {
        this.address2 = address2;
        return this;
    }

    public String getTown() {
        return town;
    }

    public MyRewardsUserRequest withTown(String town) {
        this.town = town;
        return this;
    }

    public String getPostcode() {
        return postcode;
    }

    public MyRewardsUserRequest withPostcode(String postcode) {
        this.postcode = postcode;
        return this;
    }

    public String getCounty() {
        return county;
    }

    public MyRewardsUserRequest withCounty(String county) {
        this.county = county;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public MyRewardsUserRequest withCountry(String country) {
        this.country = country;
        return this;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public MyRewardsUserRequest withDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public String getTelephone() {
        return telephone;
    }

    public MyRewardsUserRequest withTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public MyRewardsUserRequest withMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getChosenLocale() {
        return chosenLocale;
    }

    public MyRewardsUserRequest withChosenLocale(String chosenLocale) {
        this.chosenLocale = chosenLocale;
        return this;
    }

    public Boolean isTsandcs() {
        return tsandcs;
    }

    public MyRewardsUserRequest withTsandcs(Boolean tsandcs) {
        this.tsandcs = tsandcs;
        return this;
    }

    public Integer getUserGroupId() {
        return userGroupId;
    }

    public MyRewardsUserRequest withUserGroupId(Integer userGroupId) {
        this.userGroupId = userGroupId;
        return this;
    }

    public Boolean isConsented() {
        return consented;
    }

    public MyRewardsUserRequest withConsented(Boolean consented) {
        this.consented = consented;
        return this;
    }

    public Boolean isMarketingConsented() {
        return marketingConsented;
    }

    public MyRewardsUserRequest withMarketingConsented(Boolean marketingConsented) {
        this.marketingConsented = marketingConsented;
        return this;
    }

    public List<MyRewardsRegistrationAnswerAttribute> getRegistrationAnswersAttributes() {
        return registrationAnswersAttributes;
    }

    public MyRewardsUserRequest withRegistrationAnswersAttributes(List<MyRewardsRegistrationAnswerAttribute> registrationAnswersAttributes) {
        this.registrationAnswersAttributes = registrationAnswersAttributes;
        return this;
    }

    public MyRewardsUserRequest addRegistrationAnswersAttribute(MyRewardsRegistrationAnswerAttribute attribute) {
        if (registrationAnswersAttributes == null) {
            registrationAnswersAttributes = new ArrayList<>();
        }
        registrationAnswersAttributes.add(attribute);
        return this;
    }
}

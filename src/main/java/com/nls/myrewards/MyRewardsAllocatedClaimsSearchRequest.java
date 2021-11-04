package com.nls.myrewards;

import org.joda.time.LocalDate;

public class MyRewardsAllocatedClaimsSearchRequest {

    private int promotionId;
    private Integer userGroupId;
    private Integer companyId;
    private String status;
    private LocalDate createdAtStartDate;
    private LocalDate createdAtEndDate;
    private LocalDate dateOfSaleStartDate;
    private LocalDate dateOfSaleEndDate;
    private int page = 1;

    protected MyRewardsAllocatedClaimsSearchRequest() {
    }

    public MyRewardsAllocatedClaimsSearchRequest(int promotionId) {
        this.promotionId = promotionId;
    }

    public int getPromotionId() {
        return promotionId;
    }

    public Integer getUserGroupId() {
        return userGroupId;
    }

    public MyRewardsAllocatedClaimsSearchRequest withUserGroupId(Integer userGroupId) {
        this.userGroupId = userGroupId;
        return this;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public MyRewardsAllocatedClaimsSearchRequest withCompanyId(Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public MyRewardsAllocatedClaimsSearchRequest withStatus(String status) {
        this.status = status;
        return this;
    }

    public LocalDate getCreatedAtStartDate() {
        return createdAtStartDate;
    }

    public MyRewardsAllocatedClaimsSearchRequest withCreatedAtStartDate(LocalDate createdAtStartDate) {
        this.createdAtStartDate = createdAtStartDate;
        return this;
    }

    public LocalDate getCreatedAtEndDate() {
        return createdAtEndDate;
    }

    public MyRewardsAllocatedClaimsSearchRequest withCreatedAtEndDate(LocalDate createdAtEndDate) {
        this.createdAtEndDate = createdAtEndDate;
        return this;
    }

    public LocalDate getDateOfSaleStartDate() {
        return dateOfSaleStartDate;
    }

    public MyRewardsAllocatedClaimsSearchRequest withDateOfSaleStartDate(LocalDate dateOfSaleStartDate) {
        this.dateOfSaleStartDate = dateOfSaleStartDate;
        return this;
    }

    public LocalDate getDateOfSaleEndDate() {
        return dateOfSaleEndDate;
    }

    public MyRewardsAllocatedClaimsSearchRequest withDateOfSaleEndDate(LocalDate dateOfSaleEndDate) {
        this.dateOfSaleEndDate = dateOfSaleEndDate;
        return this;
    }

    public int getPage() {
        return page;
    }

    public MyRewardsAllocatedClaimsSearchRequest withPage(int page) {
        this.page = page;
        return this;
    }
}

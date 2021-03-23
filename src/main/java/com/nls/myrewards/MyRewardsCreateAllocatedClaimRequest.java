package com.nls.myrewards;

import org.joda.time.LocalDate;

import java.util.List;

public class MyRewardsCreateAllocatedClaimRequest {
    private Integer promotionId;
    private LocalDate saleDate;
    private String productOrActivityRef;
    private String invoice;
    private int quantity;
    private Integer userGroupId;
    private Integer companyId;

    public MyRewardsCreateAllocatedClaimRequest(LocalDate saleDate, int quantity) {
        this.saleDate = saleDate;
        this.quantity = quantity;
    }

    public Integer getPromotionId() {
        return promotionId;
    }

    public MyRewardsCreateAllocatedClaimRequest withPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
        return this;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public MyRewardsCreateAllocatedClaimRequest withSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
        return this;
    }

    public String getInvoice() {
        return invoice;
    }

    public MyRewardsCreateAllocatedClaimRequest withInvoice(String invoice) {
        this.invoice = invoice;
        return this;
    }

    public String getProductOrActivityRef() {
        return productOrActivityRef;
    }

    public MyRewardsCreateAllocatedClaimRequest withProductOrActivityRef(String productOrActivityRef) {
        this.productOrActivityRef = productOrActivityRef;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public MyRewardsCreateAllocatedClaimRequest withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public Integer getUserGroupId() {
        return userGroupId;
    }

    public MyRewardsCreateAllocatedClaimRequest withUserGroupId(Integer userGroupId) {
        this.userGroupId = userGroupId;
        return this;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public MyRewardsCreateAllocatedClaimRequest withCompanyId(Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    public static class ListWrapper {
        private List<MyRewardsCreateAllocatedClaimRequest> allocatedClaims;

        ListWrapper() {
        }

        ListWrapper(List<MyRewardsCreateAllocatedClaimRequest> allocatedClaims) {
            this.allocatedClaims = allocatedClaims;
        }

        public List<MyRewardsCreateAllocatedClaimRequest> getAllocatedClaims() {
            return allocatedClaims;
        }
    }

}

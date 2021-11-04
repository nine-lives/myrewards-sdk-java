package com.nls.myrewards;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import org.joda.time.LocalDate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyRewardsCreateAllocatedClaimRequest {
    private Integer promotionId;
    private LocalDate saleDate;
    private String productOrActivityRef;
    private int quantity;
    private Integer userGroupId;
    private Integer companyId;
    private String companyIdentifier;

    @JsonAnyGetter
    @JsonAnySetter
    private Map<String, Object> customFields = new HashMap<>();

    protected MyRewardsCreateAllocatedClaimRequest() {

    }

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

    public String getCompanyIdentifier() {
        return companyIdentifier;
    }

    public MyRewardsCreateAllocatedClaimRequest withCompanyIdentifier(String companyIdentifier) {
        this.companyIdentifier = companyIdentifier;
        return this;
    }

    public Map<String, Object> getCustomFields() {
        return Collections.unmodifiableMap(customFields);
    }

    public Object getCustomField(String field) {
        return customFields.get(field);
    }

    public MyRewardsCreateAllocatedClaimRequest withCustomField(String field, Object value) {
        customFields.put(field, value);
        return this;
    }

    public MyRewardsCreateAllocatedClaimRequest removeCustomField(String field) {
        customFields.remove(field);
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

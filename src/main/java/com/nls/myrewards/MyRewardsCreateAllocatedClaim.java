package com.nls.myrewards;

import com.fasterxml.jackson.core.type.TypeReference;
import org.joda.time.LocalDate;

import java.util.List;

public class MyRewardsCreateAllocatedClaim {
    public static final TypeReference<List<MyRewardsCreateAllocatedClaim>> LIST_TYPE_REFERENCE = new TypeReference<List<MyRewardsCreateAllocatedClaim>>() { };

    private int id;
    private int promotionId;
    private LocalDate saleDate;
    private String invoice;
    private String productOrActivityRef;
    private int quantity;
    private int userGroupId;
    private int companyId;

    public int getId() {
        return id;
    }

    public int getPromotionId() {
        return promotionId;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public String getInvoice() {
        return invoice;
    }

    public String getProductOrActivityRef() {
        return productOrActivityRef;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getUserGroupId() {
        return userGroupId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public static class ListWrapper {
        private List<MyRewardsCreateAllocatedClaim> allocatedClaims;

        ListWrapper() {
        }

        ListWrapper(List<MyRewardsCreateAllocatedClaim> allocatedClaims) {
            this.allocatedClaims = allocatedClaims;
        }

        public List<MyRewardsCreateAllocatedClaim> getAllocatedClaims() {
            return allocatedClaims;
        }
    }
}

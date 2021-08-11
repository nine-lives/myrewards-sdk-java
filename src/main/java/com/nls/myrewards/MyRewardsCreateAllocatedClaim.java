package com.nls.myrewards;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nls.myrewards.util.LocalDates;
import org.joda.time.LocalDate;

import java.util.List;

public class MyRewardsCreateAllocatedClaim {
    public static final TypeReference<List<MyRewardsCreateAllocatedClaim>> LIST_TYPE_REFERENCE = new TypeReference<List<MyRewardsCreateAllocatedClaim>>() { };

    private int id;
    private String saleDate;
    private String invoice;
    private String productOrActivityRef;
    private int quantity;
    private int userGroupId;
    private int companyId;
    private String companyName;
    private String companyIdentifier;
    private String status;
    private String createdAt;

    public int getId() {
        return id;
    }

    public LocalDate getSaleDate() {
        return LocalDates.tryParse(saleDate);
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

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyIdentifier() {
        return companyIdentifier;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getCreatedAt() {
        return LocalDates.tryParse(createdAt);
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

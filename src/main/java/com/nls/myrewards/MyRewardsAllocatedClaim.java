package com.nls.myrewards;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.nls.myrewards.util.LocalDates;
import org.joda.time.LocalDate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyRewardsAllocatedClaim {
    //public static final TypeReference<List<MyRewardsAllocatedClaim>> LIST_TYPE_REFERENCE = new TypeReference<List<MyRewardsAllocatedClaim>>() { };

    private int id;
    private String saleDate;
    private String productOrActivityRef;
    private int quantity;
    private int userGroupId;
    private int companyId;
    private String companyName;
    private String companyIdentifier;
    private String status;
    private String createdAt;

    @JsonAnyGetter
    @JsonAnySetter
    private Map<String, Object> customFields = new HashMap<>();


    public int getId() {
        return id;
    }

    public LocalDate getSaleDate() {
        return LocalDates.tryParse(saleDate);
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

    public Map<String, Object> getCustomFields() {
        return customFields;
    }

    public Object getCustomField(String field) {
        return customFields.get(field);
    }

    public LocalDate getCreatedAt() {
        return LocalDates.tryParse(createdAt);
    }

    public static class ListWrapper {
        private List<MyRewardsAllocatedClaim> allocatedClaims;

        ListWrapper() {
        }

        ListWrapper(List<MyRewardsAllocatedClaim> allocatedClaims) {
            this.allocatedClaims = allocatedClaims;
        }

        public List<MyRewardsAllocatedClaim> getAllocatedClaims() {
            return allocatedClaims;
        }
    }
}

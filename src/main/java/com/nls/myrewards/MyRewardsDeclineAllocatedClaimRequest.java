package com.nls.myrewards;

public class MyRewardsDeclineAllocatedClaimRequest {
    private String reasonForDeclineId;
    private String reasonForDeclineText;

    public String getReasonForDeclineId() {
        return reasonForDeclineId;
    }

    public MyRewardsDeclineAllocatedClaimRequest withReasonForDeclineId(String reasonForDeclineId) {
        this.reasonForDeclineId = reasonForDeclineId;
        return this;
    }

    public String getReasonForDeclineText() {
        return reasonForDeclineText;
    }

    public MyRewardsDeclineAllocatedClaimRequest withReasonForDeclineText(String reasonForDeclineText) {
        this.reasonForDeclineText = reasonForDeclineText;
        return this;
    }
}

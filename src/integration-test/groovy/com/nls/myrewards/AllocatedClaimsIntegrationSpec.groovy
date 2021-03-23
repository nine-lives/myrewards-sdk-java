package com.nls.myrewards

import org.joda.time.LocalDate


class AllocatedClaimsIntegrationSpec extends BaseIntegrationSpec {

    def "I get an error when I send an empty allocated claim request"() {
        when:
        client.createAllocatedClaims(298, [])

        then:
        MyRewardsServerException e = thrown(MyRewardsServerException);
        e.message == '400: Bad Request - Missing required key: allocated_claims'
    }

    def "I can create an allocated claim"() {
        when:
        MyRewardsCreateAllocatedClaim claim = client.createAllocatedClaims(298, [
                new MyRewardsCreateAllocatedClaimRequest(LocalDate.parse('2021-02-01'), 101)
                    .withCompanyId(19457)
                    .withInvoice("TESTINV")
                    //.withProductOrActivityRef('TEST-a9e6e235-58ec-4e48-822d-80b49daa9ea6')]) // 14421
                    .withProductOrActivityRef('10RS003UUK')]).get(0)

        then:
        claim.id > 0
        claim.saleDate == LocalDate.parse('2021-02-01')
        claim.productOrActivityRef == '10RS003UUK'
        claim.invoice == 'TESTINV'

        //422: Unprocessable Entity - Invoice Please complete this field
        //404: Not Found - No reward points found for product ref and date supplied
        //422: Unprocessable Entity - User Group or Company is not eligible for promotion

    }
}

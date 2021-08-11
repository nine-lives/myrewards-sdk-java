package com.nls.myrewards

import com.nls.myrewards.client.ObjectMapperFactory
import org.apache.commons.logging.LogFactory
import org.apache.commons.logging.impl.SimpleLog
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
        println(ObjectMapperFactory.make().writeValueAsString(claim))

        then:
        claim.id > 0
        claim.saleDate == LocalDate.parse('2021-02-01')
        claim.productOrActivityRef == '10RS003UUK'
        claim.invoice == 'TESTINV'

        //422: Unprocessable Entity - Invoice Please complete this field
        //404: Not Found - No reward points found for product ref and date supplied
        //422: Unprocessable Entity - User Group or Company is not eligible for promotion

    }

//    def "I can decline an allocated claim request"() {
//        when:
//        client.declineAllocatedClaims(298, 780609, new MyRewardsDeclineAllocatedClaimRequest()
//            .withReasonForDeclineText("Expired"))
//
//        then:
//        noExceptionThrown()
//    }

//    def "I can get allocated claims"() {
//        when:
//        List<MyRewardsCreateAllocatedClaim> claims = client.getAllocatedClaims(new MyRewardsAllocatedClaimsSearchRequest(298)
//            .withStatus("pending")
//            .withCompanyId(21024)
//            .withDateOfSaleEndDate(LocalDate.now().minusMonths(3)))
//
//        then:
//        println claims.size()
//        claims.forEach {println(ObjectMapperFactory.make().writeValueAsString(it)) }
//    }

}

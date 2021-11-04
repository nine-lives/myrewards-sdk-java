package com.nls.myrewards

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.nls.myrewards.client.ObjectMapperFactory
import org.joda.time.LocalDate
import spock.lang.Specification

class MyRewardsCreateAllocatedClaimRequestSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can covert a request to a payload"() {
        when:
        MyRewardsCreateAllocatedClaimRequest request = new MyRewardsCreateAllocatedClaimRequest(LocalDate.parse('2018-01-01'), 3)

        then:
        request.saleDate == LocalDate.parse('2018-01-01')
        request.quantity == 3

        when:
        request.withPromotionId(100)
                .withSaleDate(LocalDate.parse('2018-02-01'))
                .withProductOrActivityRef('AAA1234')
                .withQuantity(2)
                .withUserGroupId(3)
                .withCompanyId(5)
                .withCompanyIdentifier('company799988')
                .withCustomField('some_other_custom_field', 'some other custom answer')
                .withCustomField('invoice', 'INV1234')

        String json = mapper.writeValueAsString(request)
        Map<String, Object> entity = mapper.readValue(json, new TypeReference<Map<String, Object>>() {})

        then:
        request.promotionId == 100
        request.saleDate == LocalDate.parse('2018-02-01')
        request.productOrActivityRef == 'AAA1234'
        request.quantity == 2
        request.userGroupId == 3
        request.companyId == 5
        request.companyIdentifier == 'company799988'
        request.getCustomFields().size() == 2
        request.getCustomField('some_other_custom_field') == 'some other custom answer'
        request.customFields.some_other_custom_field == 'some other custom answer'
        request.customFields.invoice == 'INV1234'

        entity.promotion_id == 100
        entity.sale_date == '2018-02-01'
        entity.product_or_activity_ref == 'AAA1234'
        entity.quantity == 2
        entity.user_group_id == 3
        entity.company_id == 5
        entity.company_identifier == 'company799988'
        entity.some_other_custom_field == 'some other custom answer'
        entity.invoice == 'INV1234'

        when:
        MyRewardsCreateAllocatedClaimRequest result = mapper.readValue(json, MyRewardsCreateAllocatedClaimRequest)

        then:
        result.promotionId == 100
        result.saleDate == LocalDate.parse('2018-02-01')
        result.productOrActivityRef == 'AAA1234'
        result.quantity == 2
        result.userGroupId == 3
        result.companyId == 5
        result.companyIdentifier == 'company799988'
        result.customFields.size() == 2
        result.getCustomField('some_other_custom_field') == 'some other custom answer'
        request.customFields.some_other_custom_field == 'some other custom answer'
        request.customFields.invoice == 'INV1234'

        when:
        result.removeCustomField('some_other_custom_field')

        then:
        result.customFields.size() == 1

        when:
        MyRewardsCreateAllocatedClaimRequest.ListWrapper wrapper = new MyRewardsCreateAllocatedClaimRequest.ListWrapper(Collections.singletonList(request))

        then:
        wrapper.allocatedClaims.size() == 1
    }
}

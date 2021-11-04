package com.nls.myrewards

import com.fasterxml.jackson.databind.ObjectMapper
import com.nls.myrewards.client.ObjectMapperFactory
import org.joda.time.LocalDate
import spock.lang.Specification

class MyRewardsAllocatedClaimSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
            {
                "id": 1,
                "sale_date": "01/02/2018",
                "product_or_activity_ref": "AAA1234",
                "quantity": 2,
                "user_group_id": 2,
                "company_id": 5,
                "company_name": "Company C",
                "company_identifier": "company799988",
                "status": "pending",
                "created_at": "02/02/2018",
                "invoice": "INV1234",
                "some_custom_field": "some custom answer"
            }
       '''

        when:
        MyRewardsAllocatedClaim entity = mapper.readValue(payload, MyRewardsAllocatedClaim)

        then:
        entity.id == 1
        entity.saleDate == LocalDate.parse('2018-02-01')
        entity.productOrActivityRef == 'AAA1234'
        entity.quantity == 2
        entity.userGroupId == 2
        entity.companyId == 5
        entity.companyName == 'Company C'
        entity.companyIdentifier == 'company799988'
        entity.status == 'pending'
        entity.createdAt ==  LocalDate.parse('2018-02-02')
        entity.customFields.size() == 2
        entity.customFields.invoice == 'INV1234'
        entity.getCustomField('invoice') == 'INV1234'
        entity.getCustomField('some_custom_field') == 'some custom answer'
    }

    def "I can covert a list JSON payload to the entity"() {
        given:
        String payload = '''
            {
              "allocated_claims":
                [
                    {
                        "id": 1,
                        "sale_date": "01/02/2018",
                        "product_or_activity_ref": "AAA1234",
                        "quantity": 2,
                        "user_group_id": 2,
                        "company_id": 5,
                        "company_name": "Company C",
                        "company_identifier": "company799988",
                        "status": "pending",
                        "created_at": "01/02/2018",
                        "some_custom_field": "some custom answer"
                    },
                    {
                        "id": 2,
                        "sale_date": "01/02/2018",
                        "product_or_activity_ref": "AAA1234",
                        "quantity": 1,
                        "user_group_id": 2,
                        "company_id": 5,
                        "company_name": "Company C",
                        "company_identifier": "company799988",
                        "status": "pending",
                        "created_at": "01/02/2018",
                        "some_custom_field": "another custom answer"
                    }
                ]
            }
       '''

        when:
        List<MyRewardsAllocatedClaim> entity = mapper.readValue(payload, MyRewardsAllocatedClaim.ListWrapper).allocatedClaims

        then:
        entity.size() == 2
        entity[0].id == 1
        entity[1].id == 2

        when:
        MyRewardsAllocatedClaim.ListWrapper wrapper = new MyRewardsAllocatedClaim.ListWrapper(entity)

        then:
        wrapper.allocatedClaims.size() == 2
        wrapper.allocatedClaims[0].id == 1
        wrapper.allocatedClaims[1].id == 2
    }

}

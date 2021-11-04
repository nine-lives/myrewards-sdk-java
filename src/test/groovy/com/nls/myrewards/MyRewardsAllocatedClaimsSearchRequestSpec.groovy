package com.nls.myrewards

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.nls.myrewards.client.ObjectMapperFactory
import org.joda.time.LocalDate
import spock.lang.Specification

class MyRewardsAllocatedClaimsSearchRequestSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can covert a request to a payload"() {
        given:
        MyRewardsAllocatedClaimsSearchRequest request = new MyRewardsAllocatedClaimsSearchRequest(300)
                .withCompanyId(100)
                .withCreatedAtEndDate(LocalDate.parse('2020-10-13'))
                .withCreatedAtStartDate(LocalDate.parse('2020-09-13'))
                .withDateOfSaleEndDate(LocalDate.parse('2020-08-13'))
                .withDateOfSaleStartDate(LocalDate.parse('2020-07-13'))
                .withPage(3)
                .withStatus('pending')
                .withUserGroupId(200)

        when:
        String json = mapper.writeValueAsString(request);
        Map<String, Object> entity = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});

        then:
        request.promotionId == 300
        request.companyId == 100
        request.createdAtEndDate == LocalDate.parse('2020-10-13')
        request.createdAtStartDate == LocalDate.parse('2020-09-13')
        request.dateOfSaleEndDate == LocalDate.parse('2020-08-13')
        request.dateOfSaleStartDate == LocalDate.parse('2020-07-13')
        request.page == 3
        request.status == 'pending'
        request.userGroupId == 200

        entity.promotion_id == 300
        entity.company_id == 100
        entity.created_at_end_date == '2020-10-13'
        entity.created_at_start_date == '2020-09-13'
        entity.date_of_sale_end_date == '2020-08-13'
        entity.date_of_sale_start_date == '2020-07-13'
        entity.page == 3
        entity.status == 'pending'
        entity.user_group_id == 200


        when:
        MyRewardsAllocatedClaimsSearchRequest result = mapper.readValue(json, MyRewardsAllocatedClaimsSearchRequest)

        then:
        result.companyId == 100
        result.createdAtEndDate == LocalDate.parse('2020-10-13')
        result.createdAtStartDate == LocalDate.parse('2020-09-13')
        result.dateOfSaleEndDate == LocalDate.parse('2020-08-13')
        result.dateOfSaleStartDate == LocalDate.parse('2020-07-13')
        result.page == 3
        result.status == 'pending'
        result.userGroupId == 200
    }

    def "I can copy user values"() {
        given:
        String payload = '''
            {
              "user_group_id": 200,
              "company_id": 100,
              "status": "pending",
              "created_at_start_date": "2020-09-13",
              "created_at_end_date": "2020-10-13",
              "date_of_sale_start_date": "2020-07-13",
              "date_of_sale_end_date": "2020-08-13",
              "page": 3
            }
       '''

        when:
        MyRewardsAllocatedClaimsSearchRequest result = mapper.readValue(payload, MyRewardsAllocatedClaimsSearchRequest)

        then:
        result.companyId == 100
        result.createdAtEndDate == LocalDate.parse('2020-10-13')
        result.createdAtStartDate == LocalDate.parse('2020-09-13')
        result.dateOfSaleEndDate == LocalDate.parse('2020-08-13')
        result.dateOfSaleStartDate == LocalDate.parse('2020-07-13')
        result.page == 3
        result.status == 'pending'
        result.userGroupId == 200
    }
}

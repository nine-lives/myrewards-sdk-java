package com.nls.myrewards

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.nls.myrewards.client.ObjectMapperFactory
import spock.lang.Specification

class MyRewardsCompanyRequestSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can covert a request to a payload"() {
        given:
        MyRewardsCompanyRequest request = new MyRewardsCompanyRequest()
                .withName("Company name")
                .withIdentifier("company-identifier")
                .withDisabled(false)
                .withEarningType(EarningType.company)

        when:
        String json = mapper.writeValueAsString(request);
        Map<String, Object> entity = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});

        then:
        request.name == 'Company name'
        request.identifier == 'company-identifier'
        !request.disabled
        request.earningType == EarningType.company

        entity.name == 'Company name'
        entity.identifier == 'company-identifier'
        entity.disabled == 'N'
        entity.earning_type == 'company'


        when:
        MyRewardsCompanyRequest result = mapper.readValue(json, MyRewardsCompanyRequest)

        then:
        result.name == 'Company name'
        result.identifier == 'company-identifier'
        !result.disabled
        result.earningType == EarningType.company
    }

    def "I can copy user values"() {
        given:
        String payload = '''
            {
              "id": 123,
              "name": "Company name",
              "identifier": "company-identifier",
              "disabled": true,
              "earning_type": "company",
              "created_at": "2021-03-18T02:20:06.000+00:00",
              "updated_at": "2021-03-18T02:20:06.000+00:00"
            }
       '''
        MyRewardsCompany entity = mapper.readValue(payload, MyRewardsCompany)

        when:
        MyRewardsCompanyRequest result = new MyRewardsCompanyRequest(entity)

        then:
        result.name == 'Company name'
        result.identifier == 'company-identifier'
        result.disabled
        result.earningType == EarningType.company
    }
}
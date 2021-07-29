package com.nls.myrewards


import com.fasterxml.jackson.databind.ObjectMapper
import com.nls.myrewards.client.ObjectMapperFactory
import org.joda.time.DateTime
import spock.lang.Specification

class MyRewardsCompanySpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
            {
              "id": 1477,
              "name" : "Company B",
              "identifier" : "company-b-identifier",
              "disabled" : true,
              "earning_type" : "company",
              "created_at" : "2021-03-18T02:20:06.000+00:00",
              "updated_at" : "2021-03-20T13:42:17.000+00:00"
            }
       '''

        when:
        MyRewardsCompany entity = mapper.readValue(payload, MyRewardsCompany)

        then:
        entity.id == 1477
        entity.name == 'Company B'
        entity.identifier == 'company-b-identifier'
        entity.disabled
        entity.earningType == EarningType.company
        entity.createdAt == DateTime.parse('2021-03-18T02:20:06.000+00:00')
        entity.updatedAt == DateTime.parse('2021-03-20T13:42:17.000+00:00')
    }

    def "I can covert a list JSON payload to the entity"() {
        given:
        String payload = '''
            [
              {
                "id": 1423,
                "name" : "Company A",
                "identifier" : "company-a-identifier",
                "disabled" : false,
                "earning_type" : "individual",
                "created_at" : "2021-03-18T02:20:02.000+00:00",
                "updated_at" : "2021-03-19T03:20:09.000+00:00"
              },
              {
                "id": 1477,
                "name" : "Company B",
                "identifier" : "company-b-identifier",
                "disabled" : true,
                "earning_type" : "company",
                "created_at" : "2021-03-18T02:20:06.000+00:00",
                "updated_at" : "2021-03-20T13:42:17.000+00:00"
              }
            ]
       '''

        when:
        List<MyRewardsCompany> entity = mapper.readValue(payload, MyRewardsCompany.LIST_TYPE_REFERENCE)

        then:
        entity.size() == 2
        entity[0].id == 1423
        entity[1].id == 1477
    }
}
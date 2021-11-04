package com.nls.myrewards

import com.fasterxml.jackson.databind.ObjectMapper
import com.nls.myrewards.client.ObjectMapperFactory
import spock.lang.Specification

class MyRewardsPerformanceCategorySpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
            {
                "id": 1,
                "name": "Category Name 1"
            }
       '''

        when:
        MyRewardsPerformanceCategory entity = mapper.readValue(payload, MyRewardsPerformanceCategory)

        then:
        entity.id == 1
        entity.name == 'Category Name 1'
    }

    def "I can covert a list JSON payload to the entity"() {
        given:
        String payload = '''
            [
                {
                    "id": 1,
                    "name": "Category Name 1"
                },
                {
                    "id": 2,
                    "name": "Category Name 2"
                }
            ]
       '''

        when:
        List<MyRewardsPerformanceCategory> entity = mapper.readValue(payload, MyRewardsPerformanceCategory.LIST_TYPE_REFERENCE)

        then:
        entity.size() == 2
        entity[0].id == 1
        entity[0].name == 'Category Name 1'
        entity[1].id == 2
        entity[1].name == 'Category Name 2'
    }
}

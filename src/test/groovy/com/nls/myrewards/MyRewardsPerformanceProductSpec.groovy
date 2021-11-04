package com.nls.myrewards

import com.fasterxml.jackson.databind.ObjectMapper
import com.nls.myrewards.client.ObjectMapperFactory
import spock.lang.Specification

class MyRewardsPerformanceProductSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
            {
                "id": 1,
                "peformance_category_id": 1,
                "name": "Test Product 1",
                "ref": "abc1236",
                "product_type": "product",
                "value": 10,
                "description": "some description"
            }
       '''

        when:
        MyRewardsPerformanceProduct entity = mapper.readValue(payload, MyRewardsPerformanceProduct)

        then:
        entity.id == 1
        entity.performanceCategoryId == 1
        entity.name == 'Test Product 1'
        entity.ref == 'abc1236'
        entity.productType == 'product'
        entity.value == 10
        entity.description == 'some description'
    }

    def "I can covert a list JSON payload to the entity"() {
        given:
        String payload = '''
            [
                {
                    "id": 1,
                    "peformance_category_id": 1,
                    "name": "Test Product 1",
                    "ref": "abc1236",
                    "product_type": "product",
                    "value": 10,
                    "description": "some description"
                },
                {
                    "id": 2,
                    "peformance_category_id": 1,
                    "name": "Test Product 2",
                    "ref": "abc5766",
                    "product_type": "product",
                    "value": 10,
                    "description": "some description"
                }
            ]
       '''

        when:
        List<MyRewardsPerformanceProduct> entity = mapper.readValue(payload, MyRewardsPerformanceProduct.LIST_TYPE_REFERENCE)

        then:
        entity.size() == 2
        entity[0].id == 1
        entity[1].id == 2
    }
}
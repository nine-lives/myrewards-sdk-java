package com.nls.myrewards

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.nls.myrewards.client.ObjectMapperFactory
import spock.lang.Specification

class MyRewardsPerformanceProductRequestSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can covert a request to a payload"() {
        given:
        MyRewardsPerformanceProductRequest request = new MyRewardsPerformanceProductRequest()
                .withId(99)
                .withPerformanceCategoryId(100)
                .withName('Test Product 1')
                .withRef('abc1236')
                .withProductType('product')
                .withValue(10)
                .withDescription('some description')

        when:
        String json = mapper.writeValueAsString(request)
        Map<String, Object> entity = mapper.readValue(json, new TypeReference<Map<String, Object>>() {})

        then:
        request.id == 99
        request.performanceCategoryId == 100
        request.name == 'Test Product 1'
        request.ref == 'abc1236'
        request.productType == 'product'
        request.value == 10
        request.description == 'some description'

        entity.id == 99
        entity.performance_category_id == 100
        entity.name == 'Test Product 1'
        entity.ref == 'abc1236'
        entity.product_type == 'product'
        entity.value == 10
        entity.description == 'some description'

        when:
        MyRewardsPerformanceProductRequest result = mapper.readValue(json, MyRewardsPerformanceProductRequest)

        then:
        result.id == 99
        result.performanceCategoryId == 100
        result.name == 'Test Product 1'
        result.ref == 'abc1236'
        result.productType == 'product'
        result.value == 10
        result.description == 'some description'

        when:
        MyRewardsPerformanceProductRequest.ListWrapper wrapper = new MyRewardsPerformanceProductRequest.ListWrapper(Collections.singletonList(result))

        then:
        wrapper.performanceProducts.size() == 1
        wrapper.performanceProducts.get(0).performanceCategoryId == 100
    }
}

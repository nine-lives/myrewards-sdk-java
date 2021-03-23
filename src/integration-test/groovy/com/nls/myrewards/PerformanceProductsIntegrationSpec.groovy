package com.nls.myrewards

import com.nls.myrewards.client.ObjectMapperFactory

class PerformanceProductsIntegrationSpec extends BaseIntegrationSpec {

    def "I can create a performance product"() {
        when:
        String uuid = UUID.randomUUID().toString()
        List<MyRewardsPerformanceProduct> products = client.createPerformanceProducts([
                new MyRewardsPerformanceProductRequest()
                        .withPerformanceCategoryId(669)
                        .withName('Black Floppy Disk Drive ' + uuid)
                        .withRef('TEST-' + uuid)
                        .withProductType('product')
                        .withValue(1)
                        .withDescription('This is a test product from an automated testing suite')

        ])
        products.each { println(ObjectMapperFactory.make().writeValueAsString(it)) }

        then:
        products.size() > 0
    }

}

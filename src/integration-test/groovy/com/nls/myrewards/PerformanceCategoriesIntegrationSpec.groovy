package com.nls.myrewards

import com.nls.myrewards.client.ObjectMapperFactory

class PerformanceCategoriesIntegrationSpec extends BaseIntegrationSpec {

    def "I get the performance categories"() {
        when:
        List<MyRewardsPerformanceCategory> categories = client.performanceCategories
        categories.each { println(ObjectMapperFactory.make().writeValueAsString(it)) }

        then:
        categories.size() > 0
    }

}

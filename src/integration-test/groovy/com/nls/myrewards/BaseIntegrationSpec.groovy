package com.nls.myrewards

import com.nls.myrewards.client.ObjectMapperFactory
import spock.lang.Specification

abstract class BaseIntegrationSpec extends Specification {

    protected static MyRewards client

    def setupSpec() {
        ObjectMapperFactory.setFailOnUnknownProperties(true)
        client = MyRewards.make(new MyRewardsConfiguration()
                .withEndpoint(System.getProperty("myrewardsEndpoint") ?: System.getenv("myrewardsEndpoint") ?: "https://staging.my-rewards.co.uk")
                .withApiKey(System.getProperty("myrewardsApiKey") ?: System.getenv("myrewardsApiKey"))
                .withSecretKey(System.getProperty("myrewardsSecretKey") ?: System.getenv("myrewardsSecretKey")))

    }
}

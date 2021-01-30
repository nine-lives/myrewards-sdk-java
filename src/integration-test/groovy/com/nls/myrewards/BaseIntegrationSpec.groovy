package com.nls.myrewards

import com.nls.myrewards.client.ObjectMapperFactory
import spock.lang.Specification

abstract class BaseIntegrationSpec extends Specification {

    protected static MyRewards client
    private MyRewardsUserGroup testingGroup;

    def setupSpec() {
        ObjectMapperFactory.setFailOnUnknownProperties(true)
        client = MyRewards.make(new MyRewardsConfiguration()
                .withEndpoint(System.getProperty("myrewardsEndpoint") ?: System.getenv("myrewardsEndpoint") ?: "https://staging.my-rewards.co.uk")
                .withApiKey(System.getProperty("myrewardsApiKey") ?: System.getenv("myrewardsApiKey"))
                .withSecretKey(System.getProperty("myrewardsSecretKey") ?: System.getenv("myrewardsSecretKey")))

    }

    protected MyRewardsUserGroup getTestingRootGroup() {
        if (testingGroup != null) {
            return testingGroup
        }

        List<MyRewardsUserGroup> list = client.userGroups
        testingGroup = list.find {it.name == 'Testing Suite Group'}

        if (testingGroup != null) {
            return testingGroup
        }

        MyRewardsUserGroupRequest request = new MyRewardsUserGroupRequest()
                .withName('Testing Suite Group')
                .withPosition(99)
        testingGroup = client.createUserGroup(request)
        return testingGroup
    }

}

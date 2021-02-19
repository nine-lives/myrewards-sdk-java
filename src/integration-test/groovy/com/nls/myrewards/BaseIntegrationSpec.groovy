package com.nls.myrewards

import com.nls.myrewards.client.ObjectMapperFactory
import spock.lang.Specification

abstract class BaseIntegrationSpec extends Specification {
    protected static final int COMPANY_MAGIC_NUMBER = 10606
    protected static MyRewards client
    private MyRewardsUserGroup testingGroup;
    private String testingCompany;

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

    protected MyRewardsUserGroup expectTestUserGroup(String name) {
        List<MyRewardsUserGroup> list = client.userGroups
        MyRewardsUserGroup group = testingGroup = list.find {it.name == name}

        if (group == null) {
            MyRewardsUserGroupRequest request = new MyRewardsUserGroupRequest()
                    .withName(name)
                    .withParentId(testingRootGroup.id)
                    .withPosition(99)
            group = client.createUserGroup(request)
        }

        return group
    }

    protected String getTestingCompany() {
        if (testingCompany != null) {
            return testingCompany
        }

        String testSuiteCompany = "Testing Corp (Test Suite)";

        MyRewardsRegistrationQuestionValue value = client.getRegistrationQuestionValue(COMPANY_MAGIC_NUMBER, testSuiteCompany);
        if (value != null) {
            testingCompany = value.name
            return testingCompany
        }

        testingCompany = client.createRegistrationQuestionValues(COMPANY_MAGIC_NUMBER, testSuiteCompany).name
        return testingCompany
    }


    protected String getRandomPhone(int index) {
        "+44${String.valueOf(System.currentTimeMillis()).reverse().drop(2).take(9).reverse()}${index}"
    }

    protected String getRandomEmail(String uuid = UUID.randomUUID().toString()) {
        "tester+${uuid}@testcorp.com"
    }

    protected String getRandomUsername(String uuid = UUID.randomUUID().toString()) {
        "tester-${uuid}"
    }
}

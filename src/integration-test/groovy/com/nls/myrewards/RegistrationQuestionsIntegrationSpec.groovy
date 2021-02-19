package com.nls.myrewards

import com.nls.myrewards.client.ObjectMapperFactory

class RegistrationQuestionsIntegrationSpec extends BaseIntegrationSpec {

    def "I can list registration questions"() {
        when:
        List<MyRewardsRegistrationQuestion> list = client.registrationQuestions
        //list.each { println(ObjectMapperFactory.make().writeValueAsString(it)) }

        then:
        list.size() == 1;
        list.get(0).id == 10703
        list.get(0).label == 'tier_level'
        list.get(0).fieldType == 'select'
        list.get(0).options == 'Bronze\r\nSilver\r\nGold'
        list.get(0).fieldName == 'tier_level'
        !list.get(0).freeText

        when:
        List<MyRewardsRegistrationQuestionValue> values = client.getRegistrationQuestionValues(list.get(0).id)
        values.each { println(ObjectMapperFactory.make().writeValueAsString(it)) }

        then:
        values.size() == 3
        values*.name as Set == ['Bronze', 'Silver', 'Gold'] as Set

    }

    def "I get an error if I request registration question values for a non-existent question"() {
        when:
        List<MyRewardsRegistrationQuestionValue> list = client.getRegistrationQuestionValues(99999999)
        //list.each { println(ObjectMapperFactory.make().writeValueAsString(it)) }

        then:
        MyRewardsServerException e = thrown(MyRewardsServerException)
        e.statusCode == 404
        e.error.errors[0] == 'registration question not found'
    }

    def "I can the magic number question for companies"() {
        when:
        List<MyRewardsRegistrationQuestionValue> list = client.getRegistrationQuestionValues(10606)
        //list.each { println(ObjectMapperFactory.make().writeValueAsString(it)) }

        then:
        !list.isEmpty();

        when:
        MyRewardsRegistrationQuestionValue value = client.getRegistrationQuestionValue(10606, 18685)

        then:
        value.id == 18685
        value.name == 'Sherpa Marketing'
    }
}

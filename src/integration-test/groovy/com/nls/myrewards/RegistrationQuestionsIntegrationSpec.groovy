package com.nls.myrewards

import com.nls.myrewards.client.ObjectMapperFactory

class RegistrationQuestionsIntegrationSpec extends BaseIntegrationSpec {

    def "I can list registration questions"() {
        when:
        List<MyRewardsRegistrationQuestion> list = client.getRegistrationQuestions()
        list.each { println(ObjectMapperFactory.make().writeValueAsString(it)) }

        then:
        list != null
        list.isEmpty()
    }

    def "I get an error if I request registration question values for a non-existent question"() {
        when:
        List<MyRewardsRegistrationQuestionValue> list = client.getRegistrationQuestionValues(99999999)
        list.each { println(ObjectMapperFactory.make().writeValueAsString(list)) }

        then:
        MyRewardsServerException e = thrown(MyRewardsServerException)
        e.statusCode == 404
        e.error.errors[0] == 'registration question not found'
    }

}

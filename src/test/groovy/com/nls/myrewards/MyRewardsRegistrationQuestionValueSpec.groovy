package com.nls.myrewards

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.nls.myrewards.util.ObjectMapperFactory
import spock.lang.Specification

class MyRewardsRegistrationQuestionValueSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
            {
                "id": 1,
                "name": "List value 1"
            }
       '''

        when:
        MyRewardsRegistrationQuestionValue entity = mapper.readValue(payload, MyRewardsRegistrationQuestionValue)

        then:
        entity.id == 1
        entity.name == 'List value 1'
    }

    def "I can covert a list JSON payload to the entity"() {
        given:
        String payload = '''
            [
              {
                "id": 1,
                "name": "List value 1"
              },
              {
                "id": 2,
                "name": "List value 2"
              }
            ]
       '''

        when:
        List<MyRewardsRegistrationQuestionValue> entity = mapper.readValue(payload, new TypeReference<List<MyRewardsRegistrationQuestionValue>>() {})

        then:
        entity.size() == 2
        entity[0].id == 1
        entity[1].id == 2
    }
}
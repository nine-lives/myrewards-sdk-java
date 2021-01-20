package com.nls.myrewards

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.nls.myrewards.util.ObjectMapperFactory
import spock.lang.Specification

class MyRewardsRegistrationQuestionSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
          {
            "id": 1234,
            "label": "text",
            "mandatory": "true",
            "field_type": "radio",
            "options": "text_1 text_2 text_3",
            "field_name" : "field_level",
            "free_text": "true"
          }
       '''

        when:
        MyRewardsRegistrationQuestion entity = mapper.readValue(payload, MyRewardsRegistrationQuestion)

        then:
        entity.id == 1234
        entity.label == 'text'
        entity.mandatory
        entity.fieldType == 'radio'
        entity.options == 'text_1 text_2 text_3'
        entity.fieldName == 'field_level'
        entity.freeText
    }

    def "I can covert a list JSON payload to the entity"() {
        given:
        String payload = '''
            [
              {
                "id": 1234,
                "label": "text",
                "mandatory": "true",
                "field_type": "radio",
                "options": "text_1 text_2 text_3",
                "field_name" : "field_level",
                "free_text": "true"
              },
              {
                "id": 1235,
                "label": "text",
                "mandatory": "true",
                "field_type": "radio",
                "options": "text_1 text_2 text_3",
                "field_name" : "field_level",
                "free_text": "true"
              }
            ]
       '''

        when:
        List<MyRewardsRegistrationQuestion> entity = mapper.readValue(payload, new TypeReference<List<MyRewardsRegistrationQuestion>>() {
        })

        then:
        entity.size() == 2
        entity[0].id == 1234
        entity[1].id == 1235
    }
}
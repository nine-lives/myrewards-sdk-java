package com.nls.myrewards

import com.fasterxml.jackson.databind.ObjectMapper
import com.nls.myrewards.client.ObjectMapperFactory
import spock.lang.Specification

class MyRewardsErrorSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can convert a JSON payload with field errors to the entity"() {
        given:
        String payload = '''
                {
                    "errors": [
                        "field1 error",
                        "field2 error"
                    ]
                }
       '''

        when:
        MyRewardsError entity = mapper.readValue(payload, MyRewardsError)

        then:
        entity.message == 'field1 error, field2 error'
        entity.errors.size() == 2
        entity.errors[0] == 'field1 error'
        entity.errors[1] == 'field2 error'
    }

    def "I can covert a JSON payload with message error to the entity"() {
        given:
        String payload = '''
                {
                    "errors": ["serious malfunction"]
                }
       '''

        when:
        MyRewardsError entity = mapper.readValue(payload, MyRewardsError)

        then:
        entity.message == 'serious malfunction'
        entity.errors[0] == 'serious malfunction'
    }

}
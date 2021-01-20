package com.nls.myrewards

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

class MyRewardsErrorSpec extends Specification {
    private ObjectMapper mapper = com.nls.myrewards.util.ObjectMapperFactory.make()

    def "I can convert a JSON payload with field errors to the entity"() {
        given:
        String payload = '''
                {
                    "error": {
                        "field1": ["field1 error"],
                        "field2": ["field2 error"]
                    }
                }
       '''

        when:
        MyRewardsError entity = mapper.readValue(payload, MyRewardsError)

        then:
        entity.message == 'Invalid request, check field errors'
        entity.fieldErrors.size() == 2
        entity.fieldErrors.field1 == ['field1 error']
        entity.fieldErrors.field2 == ['field2 error']
    }

    def "I can covert a JSON payload with message error to the entity"() {
        given:
        String payload = '''
                {
                    "error": "serious malfunction"
                }
       '''

        when:
        MyRewardsError entity = mapper.readValue(payload, MyRewardsError)

        then:
        entity.message == 'serious malfunction'
        entity.error == 'serious malfunction'
        entity.fieldErrors.size() == 0
    }

}
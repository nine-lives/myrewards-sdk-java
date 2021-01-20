package com.nls.myrewards

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.nls.myrewards.util.ObjectMapperFactory
import spock.lang.Specification

class MyRewardsUserGroupSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
            {
                "id" : 1,
                "name" : "dc comics",
                "parent_id" :  null,
                "default" : "false",
                "position" : 1
            }
       '''

        when:
        MyRewardsUserGroup entity = mapper.readValue(payload, MyRewardsUserGroup.class)

        then:
        entity.id == 1
        entity.name == 'dc comics'
        entity.parentId == null
        !entity.defaultGroup
        entity.position == 1
    }

    def "I can covert a list JSON payload to the entity"() {
        given:
        String payload = '''
            [
              {
                "id" : 1,
                "name" : "dc comics",
                "parent_id" :  null,
                "default" : "false",
                "position" : 1
              },
              {
                "id" : 2,
                "name" : "justice league",
                "parent_id" : 1,
                "default" : "true",
                "position" : 1
              }
            ]
       '''

        when:
        List<MyRewardsUserGroup> entity = mapper.readValue(payload, new TypeReference<List<MyRewardsUserGroup>>() {})

        then:
        entity.size() == 2
        entity[0].id == 1
        entity[1].id == 2
        entity[1].parentId == 1
        entity[1].defaultGroup
    }

}
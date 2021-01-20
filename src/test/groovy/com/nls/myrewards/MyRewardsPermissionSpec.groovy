package com.nls.myrewards

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.nls.myrewards.util.ObjectMapperFactory
import spock.lang.Specification

class MyRewardsPermissionSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
            {
                "id" : 6,
                "name" : "Enable log in",
                "hint" : "Allow log in",
                "resource_group_name" : "General"
            }
       '''

        when:
        MyRewardsPermission entity = mapper.readValue(payload, MyRewardsPermission.class)

        then:
        entity.id == 6
        entity.name == 'Enable log in'
        entity.hint == 'Allow log in'
        entity.resourceGroupName == 'General'
    }

    def "I can covert a list JSON payload to the entity"() {
        given:
        String payload = '''
            [
                {
                    "id" : 6,
                    "name" : "Enable log in",
                    "hint" : "Enable log in",
                    "resource_group_name" : "General"
                },
                {
                    "id" : 11,
                    "name" : "Order Rewards",
                    "hint" : "User can order rewards if they have sufficient points available",
                    "resource_group_name" : "Rewards module"
                }
            ]
       '''

        when:
        List<MyRewardsPermission> entity = mapper.readValue(payload, new TypeReference<List<MyRewardsPermission>>() {})

        then:
        entity.size() == 2
        entity[0].id == 6
        entity[1].id == 11
    }

}
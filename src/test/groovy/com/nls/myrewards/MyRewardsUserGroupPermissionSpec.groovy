package com.nls.myrewards

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.nls.myrewards.util.ObjectMapperFactory
import spock.lang.Specification

class MyRewardsUserGroupPermissionSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
            {
              "id" : 1,
              "name" : "Enable log in",
              "permission_group_name" :  "General",
              "active" : false
            }
       '''

        when:
        MyRewardsUserGroupPermission entity = mapper.readValue(payload, MyRewardsUserGroupPermission.class)

        then:
        entity.id == 1
        entity.name == 'Enable log in'
        entity.permissionGroupName == 'General'
        !entity.active
    }

    def "I can covert a list JSON payload to the entity"() {
        given:
        String payload = '''
            {
              "permissions" : [
                {
                  "id" : 1,
                  "name" : "Enable log in",
                  "permission_group_name" :  "General",
                  "active" : false
                },
                {
                  "id" : 2,
                  "name" : "Order Rewards",
                  "permission_group_name" :  "Rewards module",
                  "active" : true
                }
              ]
            }
       '''

        when:
        List<MyRewardsUserGroupPermission> entity = mapper.readValue(payload, MyRewardsUserGroupPermission.ListWrapper).getPermissions()

        then:
        entity.size() == 2
        entity[0].id == 1
        entity[1].id == 2
        !entity[0].active
        entity[1].active
    }

}
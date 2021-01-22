package com.nls.myrewards


import com.fasterxml.jackson.databind.ObjectMapper
import com.nls.myrewards.client.ObjectMapperFactory
import spock.lang.Specification

class MyRewardsUserPermissionSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
            {
              "id" : 1,
              "name" : "Enable log in",
              "permission_group_name" :  "General",
              "active" : false,
              "state" : "Same As User Group"
            }
       '''

        when:
        MyRewardsUserPermission entity = mapper.readValue(payload, MyRewardsUserPermission.class)

        then:
        entity.id == 1
        entity.name == 'Enable log in'
        entity.permissionGroupName == 'General'
        !entity.active
        entity.state == MyRewardsPermissionState.SameAsUserGroup
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
                  "active" : false,
                  "state" : "Same As User Group"
                },
                {
                  "id" : 2,
                  "name" : "Order Rewards",
                  "permission_group_name" :  "Rewards module",
                  "active" : true,
                  "state" : "Always Allow"
                }
              ]
            }
       '''

        when:
        List<MyRewardsUserPermission> entity = mapper.readValue(payload, MyRewardsUserPermission.ListWrapper).getPermissions()

        then:
        entity.size() == 2
        entity[0].id == 1
        entity[1].id == 2
        !entity[0].active
        entity[1].active
        entity[1].state == MyRewardsPermissionState.AlwaysAllow
    }
}
package com.nls.myrewards.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.nls.myrewards.MyRewardsPermission
import com.nls.myrewards.MyRewardsUserGroup
import com.nls.myrewards.MyRewardsUserGroupPermission
import com.nls.myrewards.client.ObjectMapperFactory
import spock.lang.Specification

class MyRewardsUserGroupResourcesSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can group permissions by resource"() {
        given:
        String groupPayload =
                '''
                    {
                        "id" : 15607,
                        "name" : "admins",
                        "position" : 1,
                        "default" : false
                    }
                '''

        String permissionsPayload =
                '''
                {
                    "permissions": [
                        {
                          "id" : 6,
                          "name" : "Enable log in",
                          "permission_group_name" : "General",
                          "active" : true
                        },
                        {
                          "id" : 7,
                          "name" : "Line Manager approval",
                          "permission_group_name" : "Recognitions module",
                          "active" : false
                        },
                        {
                          "id" : 8,
                          "name" : "Department Manager approval",
                          "permission_group_name" : "Recognitions module",
                          "active" : false
                        },
                        {
                          "id" : 9,
                          "name" : "Local recognition reporting",
                          "permission_group_name" : "Recognitions module",
                          "active" : false
                        },
                        {
                          "id" : 10,
                          "name" : "Global recognition reporting",
                          "permission_group_name" : "Recognitions module",
                          "active" : false
                        },
                        {
                          "id" : 11,
                          "name" : "Order Rewards",
                          "permission_group_name" : "Rewards module",
                          "active" : false
                        },
                        {
                          "id" : 12,
                          "name" : "Local Reporting",
                          "permission_group_name" : "Rewards module",
                          "active" : false
                        },
                        {
                          "id" : 13,
                          "name" : "Global Reporting",
                          "permission_group_name" : "Rewards module",
                          "active" : false
                        },
                        {
                          "id" : 14,
                          "name" : "Global Overview Reporting",
                          "permission_group_name" : "General",
                          "active" : true
                        },
                        {
                          "id" : 15,
                          "name" : "Approve claims",
                          "permission_group_name" : "Performance module",
                          "active" : false
                        },
                        {
                          "id" : 16,
                          "name" : "Local reporting",
                          "permission_group_name" : "Performance module",
                          "active" : false
                        },
                        {
                          "id" : 17,
                          "name" : "Global reporting",
                          "permission_group_name" : "Performance module",
                          "active" : false
                        },
                        {
                          "id" : 18,
                          "name" : "Local Overview Reporting",
                          "permission_group_name" : "General",
                          "active" : true
                        },
                        {
                          "id" : 19,
                          "name" : "Creator",
                          "permission_group_name" : "Program in a Box",
                          "active" : false
                        },
                        {
                          "id" : 20,
                          "name" : "Send Person2Person transfer",
                          "permission_group_name" : "Rewards module",
                          "active" : false
                        }
                     ]
                 }
                '''

        MyRewardsUserGroup group = mapper.readValue(groupPayload, MyRewardsUserGroup)
        List<MyRewardsUserGroupPermission> entity = mapper.readValue(permissionsPayload, MyRewardsUserGroupPermission.ListWrapper.class).permissions

        when:
        MyRewardsUserGroupResources resources = new MyRewardsUserGroupResources(group, entity);

        then:
        resources.resourceNames == ['General', 'Recognitions module', 'Rewards module', 'Performance module', 'Program in a Box'] as Set
        resources.activeResourceNames == ['General'] as Set

        when:
        MyRewardsUserGroupResource general = resources.getResource('General')

        then:
        general.name == 'General'
        general.permissions.size() == 3
        general.activePermissions.size() == 3
        general.hasActivePermission('Enable log in')
        general.hasActivePermission('Local Overview Reporting')
        general.hasActivePermission('Global Overview Reporting')
        general.getPermission('Enable log in').name == 'Enable log in'

        when:
        MyRewardsUserGroupResource recognitions = resources.getResource('Recognitions module')

        then:
        recognitions.name == 'Recognitions module'
        recognitions.permissions.size() == 4
        recognitions.activePermissions.size() == 0
        recognitions.hasPermission('Line Manager approval')
        !recognitions.hasActivePermission('Line Manager approval')
        !recognitions.hasActivePermission('Department Manager approval')
        !recognitions.hasActivePermission('Local recognition reporting')
        !recognitions.hasActivePermission('Global recognition reporting')

        when:
        MyRewardsUserGroupResource rewards = resources.getResource('Rewards module')

        then:
        rewards.name == 'Rewards module'
        rewards.permissions.size() == 4
        rewards.activePermissions.size() == 0
        !rewards.hasActivePermission('Order Rewards')
        !rewards.hasActivePermission('Local Reporting')
        !rewards.hasActivePermission('Global Reporting')
        !rewards.hasActivePermission('Send Person2Person transfer')

        when:
        MyRewardsUserGroupResource performance = resources.getResource('Performance module')

        then:
        performance.name == 'Performance module'
        performance.permissions.size() == 3
        performance.activePermissions.size() == 0
        !performance.hasActivePermission('Approve claims')
        !performance.hasActivePermission('Local reporting')
        !performance.hasActivePermission('Global reporting')

        when:
        MyRewardsUserGroupResource program = resources.getResource('Program in a Box')

        then:
        program.name == 'Program in a Box'
        program.permissions.size() == 1
        program.activePermissions.size() == 0
        !program.hasActivePermission('Creator')
    }
}

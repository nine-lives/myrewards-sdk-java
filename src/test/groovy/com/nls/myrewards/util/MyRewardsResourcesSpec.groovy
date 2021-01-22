package com.nls.myrewards.util


import com.fasterxml.jackson.databind.ObjectMapper
import com.nls.myrewards.MyRewardsPermission
import com.nls.myrewards.client.ObjectMapperFactory
import spock.lang.Specification

class MyRewardsResourcesSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can group permissions by resource"() {
        given:
        String payload =
                '''
                   [
                    {
                      "id" : 6,
                      "name" : "Enable log in",
                      "hint" : "Enable log in",
                      "resource_group_name" : "General"
                    },
                    {
                      "id" : 7,
                      "name" : "Line Manager approval",
                      "hint" : "First level approval rights. Any action requiring line manager approval will find the first appropriate line manager in the group structure.",
                      "resource_group_name" : "Recognitions module"
                    },
                    {
                      "id" : 8,
                      "name" : "Department Manager approval",
                      "hint" : "Second level approval rights. Any action requiring department head approval will find the first appropriate department head in the group structure.",
                      "resource_group_name" : "Recognitions module"
                    },
                    {
                      "id" : 9,
                      "name" : "Local recognition reporting",
                      "hint" : "Access to dashboard reports for all user groups below then in the structure.",
                      "resource_group_name" : "Recognitions module"
                    },
                    {
                      "id" : 10,
                      "name" : "Global recognition reporting",
                      "hint" : "Access to dashboard reports for all user groups",
                      "resource_group_name" : "Recognitions module"
                    },
                    {
                      "id" : 11,
                      "name" : "Order Rewards",
                      "hint" : "User can order rewards if they have sufficient points available",
                      "resource_group_name" : "Rewards module"
                    },
                    {
                      "id" : 12,
                      "name" : "Local Reporting",
                      "hint" : "Access to dashboard reports for all user groups below them in the structure",
                      "resource_group_name" : "Rewards module"
                    },
                    {
                      "id" : 13,
                      "name" : "Global Reporting",
                      "hint" : "Access to dashboard reports for all user groups",
                      "resource_group_name" : "Rewards module"
                    },
                    {
                      "id" : 14,
                      "name" : "Global Overview Reporting",
                      "hint" : "Access to engagement dashboard for all user groups",
                      "resource_group_name" : "General"
                    },
                    {
                      "id" : 15,
                      "name" : "Approve claims",
                      "hint" : "Allows the user access to the admin area to review and approve programme claims",
                      "resource_group_name" : "Performance module"
                    },
                    {
                      "id" : 16,
                      "name" : "Local reporting",
                      "hint" : "Access to dashboard reports for all user groups below in the structure",
                      "resource_group_name" : "Performance module"
                    },
                    {
                      "id" : 17,
                      "name" : "Global reporting",
                      "hint" : "Access to dashboard reports for all user groups",
                      "resource_group_name" : "Performance module"
                    },
                    {
                      "id" : 18,
                      "name" : "Local Overview Reporting",
                      "hint" : "Access to engagement dashboard reports for all user groups below them in the structure",
                      "resource_group_name" : "General"
                    },
                    {
                      "id" : 19,
                      "name" : "Creator",
                      "hint" : "User can create and edit boxes",
                      "resource_group_name" : "Program in a Box"
                    },
                    {
                      "id" : 20,
                      "name" : "Send Person2Person transfer",
                      "hint" : "Allow users to transfer points from their account to another programme user",
                      "resource_group_name" : "Rewards module"
                    }
                   ]
                '''
        List<MyRewardsPermission> entity = mapper.readValue(payload, MyRewardsPermission.LIST_TYPE_REFERENCE)

        when:
        MyRewardsResources resources = new MyRewardsResources(entity);

        then:
        resources.resourceNames == ['General', 'Recognitions module', 'Rewards module', 'Performance module', 'Program in a Box'] as Set

        when:
        MyRewardsResource general = resources.getResource('General')

        then:
        general.permissions.size() == 3
        general.name == 'General'
        general.hasPermission('Enable log in')
        general.hasPermission('Local Overview Reporting')
        general.hasPermission('Global Overview Reporting')
        general.getPermission('Enable log in').name == 'Enable log in'

        when:
        MyRewardsResource recognitions = resources.getResource('Recognitions module')

        then:
        recognitions.permissions.size() == 4
        recognitions.name == 'Recognitions module'
        recognitions.hasPermission('Line Manager approval')
        recognitions.hasPermission('Department Manager approval')
        recognitions.hasPermission('Local recognition reporting')
        recognitions.hasPermission('Global recognition reporting')

        when:
        MyRewardsResource rewards = resources.getResource('Rewards module')

        then:
        rewards.permissions.size() == 4
        rewards.name == 'Rewards module'
        rewards.hasPermission('Order Rewards')
        rewards.hasPermission('Local Reporting')
        rewards.hasPermission('Global Reporting')
        rewards.hasPermission('Send Person2Person transfer')

        when:
        MyRewardsResource performance = resources.getResource('Performance module')

        then:
        performance.permissions.size() == 3
        performance.name == 'Performance module'
        performance.hasPermission('Approve claims')
        performance.hasPermission('Local reporting')
        performance.hasPermission('Global reporting')

        when:
        MyRewardsResource program = resources.getResource('Program in a Box')

        then:
        program.permissions.size() == 1
        program.name == 'Program in a Box'
        program.hasPermission('Creator')

    }
}

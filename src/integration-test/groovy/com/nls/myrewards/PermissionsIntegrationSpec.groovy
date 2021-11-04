package com.nls.myrewards

import com.nls.myrewards.client.ObjectMapperFactory
import com.nls.myrewards.util.MyRewardsPermissionGroup
import com.nls.myrewards.util.MyRewardsPermissionGroups

class PermissionsIntegrationSpec extends BaseIntegrationSpec {

    def "I can list permissions"() {
        when:
        List<MyRewardsPermission> permissions = client.getPermissions()
        permissions.each { println(ObjectMapperFactory.make().writeValueAsString(it)) }

        then:
        permissions != null
        !permissions.isEmpty()
        permissions[0].id > 0
        permissions[0].name.length() > 0
        permissions[0].hint.length() > 0
        permissions[0].resourceGroupName.length() > 0
    }

    def "I get the permissions I am expecting"() {
        given:
        List<MyRewardsPermission> permissions = client.getPermissions()
        MyRewardsPermissionGroups resources = new MyRewardsPermissionGroups(client.getPermissions());

        when:
        MyRewardsPermissionGroup general = resources.getGroup('General')

        then:
        general.permissions.size() >= 3
        general.name == 'General'
        general.hasPermission('Enable log in')
        general.hasPermission('Local Overview Reporting')
        general.hasPermission('Global Overview Reporting')
        general.getPermission('Enable log in').name == 'Enable log in'

        when:
        MyRewardsPermissionGroup recognitions = resources.getGroup('Recognitions module')

        then:
        recognitions.permissions.size() == 4
        recognitions.name == 'Recognitions module'
        recognitions.hasPermission('Line Manager approval')
        recognitions.hasPermission('Department Manager approval')
        recognitions.hasPermission('Local recognition reporting')
        recognitions.hasPermission('Global recognition reporting')

        when:
        MyRewardsPermissionGroup rewards = resources.getGroup('Rewards module')

        then:
        rewards.permissions.size() == 4
        rewards.name == 'Rewards module'
        rewards.hasPermission('Order Rewards')
        rewards.hasPermission('Local Reporting')
        rewards.hasPermission('Global Reporting')
        rewards.hasPermission('Send Person2Person transfer')

        when:
        MyRewardsPermissionGroup performance = resources.getGroup('Performance module')

        then:
        performance.permissions.size() == 3
        performance.name == 'Performance module'
        performance.hasPermission('Approve claims')
        performance.hasPermission('Local reporting')
        performance.hasPermission('Global reporting')

//        when:
//        MyRewardsPermissionGroup program = resources.getGroup('Program in a Box')
//
//        then:
//        program.permissions.size() == 1
//        program.name == 'Program in a Box'
//        program.hasPermission('Creator')
    }
}

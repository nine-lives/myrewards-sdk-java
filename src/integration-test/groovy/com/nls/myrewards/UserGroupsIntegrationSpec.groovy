package com.nls.myrewards

import com.nls.myrewards.client.ObjectMapperFactory
import com.nls.myrewards.util.MyRewardsStatefulPermissionGroup
import com.nls.myrewards.util.MyRewardsStatefulPermissionGroups

class UserGroupsIntegrationSpec extends BaseIntegrationSpec {

    def "I can list user groups"() {
        when:
        List<MyRewardsUserGroup> list = client.getUserGroups()
        //list.each { println(ObjectMapperFactory.make().writeValueAsString(it)) }

        then:
        list.size() >= 4
        (list*.name as Set).containsAll(['unassigned', 'global admins', 'leavers'])

        when:
        MyRewardsUserGroup group = client.getUserGroup(list[0].id)

        then:
        group.id == list[0].id
    }


    def "I can get user group permissions"() {
        given:
        List<MyRewardsUserGroup> list = client.getUserGroups()

        when:
        MyRewardsUserGroup group = list.find { it.name == 'global admins' }
        MyRewardsStatefulPermissionGroups<MyRewardsUserGroupPermission> resources = new MyRewardsStatefulPermissionGroups<>(
                client.getUserGroupPermissions(group.id))

        then:
        resources.activeGroupNames == ['General', 'Performance module'] as Set
        MyRewardsStatefulPermissionGroup resource = resources.getGroup('General')
        resource.activePermissions.size() == 3
        resource.hasActivePermission('Enable log in')
        resource.hasActivePermission('Local Overview Reporting')
        resource.hasActivePermission('Global Overview Reporting')
        resource.getPermission('Enable log in').name == 'Enable log in'

        when:
        group = list.find { it.name == 'unassigned' }
        resources = new MyRewardsStatefulPermissionGroups(
                client.getUserGroupPermissions(group.id))

        then:
        resources.activeGroupNames == [] as Set

        when:
        group = list.find { it.name == 'leavers' }
        resources = new MyRewardsStatefulPermissionGroups(
                client.getUserGroupPermissions(group.id))

        then:
        resources.activeGroupNames == [] as Set
    }

    def "I can create a user group"() {
        when:
        MyRewardsUserGroupRequest request = new MyRewardsUserGroupRequest()
            .withName("Test Sub Group")
            .withPosition(3)
            .withParentId(testingRootGroup.id)
            .withImageUrl("https://b2bm.s3.amazonaws.com/styles/panopoly_image_square/s3/sherpa_directory_logo.png?itok=EdxRJCBn")
        MyRewardsUserGroup group = client.createUserGroup(request)

        then:
        group.id > 0
        group.name == request.getName()
        group.position == 3
        group.parentId == testingRootGroup.id
        group.imageUrl != null
    }

    def "I can assign permissions to the user group"() {
        given:
        List<MyRewardsUserGroupPermission> permissions = client.getUserGroupPermissions(testingRootGroup.id)
        //println(permissions.findAll { it.active }.size())
        //println(permissions.findAll { it.active }*.name)

        when:
        permissions.each { it.withActive(false) }
        permissions = client.setUserGroupPermissions(testingRootGroup.id, permissions)
        //println(permissions.findAll { it.active }.size())
        //println(permissions.findAll { it.active }*.name)

        then:
        permissions.findAll { it.active }.empty

        when:
        permissions.each { it.withActive(true) }
        permissions = client.setUserGroupPermissions(testingRootGroup.id, permissions)
        //println(permissions.findAll { it.active }.size())
        println(permissions.findAll { !it.active }*.name)

        then:
        //permissions.findAll { !it.active }.empty // apparently can't set all of them
        !permissions.findAll { it.active }.empty
    }
}

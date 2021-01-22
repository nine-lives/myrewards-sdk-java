package com.nls.myrewards

import com.nls.myrewards.client.ObjectMapperFactory
import com.nls.myrewards.util.MyRewardsUserGroupResource
import com.nls.myrewards.util.MyRewardsUserGroupResources
import spock.lang.Ignore

class UserGroupsIntegrationSpec extends BaseIntegrationSpec {

    def "I can list user groups"() {
        when:
        List<MyRewardsUserGroup> list = client.getUserGroups()
        list.each { println(ObjectMapperFactory.make().writeValueAsString(it)) }

        then:
        list.size() >= 4
        (list*.name as Set).containsAll(['unassigned', 'global admins', 'leavers'])
    }

    def "I can get user group permissions"() {
        given:
        List<MyRewardsUserGroup> list = client.getUserGroups()

        when:
        MyRewardsUserGroup group = list.find { it.name == 'global admins' }
        MyRewardsUserGroupResources resources = new MyRewardsUserGroupResources(
                group,
                client.getUserGroupPermissions(group.id));

        then:
        resources.activeResourceNames == ['General'] as Set
        MyRewardsUserGroupResource resource = resources.getResource('General')
        resource.activePermissions.size() == 3
        resource.hasActivePermission('Enable log in')
        resource.hasActivePermission('Local Overview Reporting')
        resource.hasActivePermission('Global Overview Reporting')
        resource.getPermission('Enable log in').name == 'Enable log in'

        when:
        group = list.find { it.name == 'unassigned' }
        resources = new MyRewardsUserGroupResources(
                group,
                client.getUserGroupPermissions(group.id));

        then:
        resources.activeResourceNames == [] as Set

        when:
        group = list.find { it.name == 'leavers' }
        resources = new MyRewardsUserGroupResources(
                group,
                client.getUserGroupPermissions(group.id));

        then:
        resources.activeResourceNames == [] as Set
    }

    @Ignore
    def "I can create a user group"() {
        when:
        MyRewardsUserGroupRequest request = new MyRewardsUserGroupRequest()
            .withName("Test Sub Group")
            .withPosition(3)
            .withParentId(15609)
            .withImageUrl("https://b2bm.s3.amazonaws.com/styles/panopoly_image_square/s3/sherpa_directory_logo.png?itok=EdxRJCBn")
        MyRewardsUserGroup group = client.createUserGroup(request)

        then:
        group.id > 0
        group.name == request.getName()
        group.position == 3
        group.parentId == 15609
        group.imageUrl != null
    }

    @Ignore
    def "I can assign permissions to the user group"() {
        given:
        List<MyRewardsUserGroupPermission> permissions = client.getUserGroupPermissions(15612)
        println(permissions.findAll { it.active }.size())
        println(permissions.findAll { it.active }*.name)

        when:
        permissions.each { it.withActive(true) }
        permissions = client.setUserGroupPermissions(15612, permissions)

        then:
        println(permissions.findAll { it.active }.size())
        println(permissions.findAll { it.active }*.name)
        permissions.findAll { it.active }.size() == 4
        (permissions.findAll { it.active }*.permissionGroupName) as Set == ['General', 'Program in a Box'] as Set

        when:
        permissions.each { it.withActive(false) }
        permissions = client.setUserGroupPermissions(15612, permissions)

        then:
        println(permissions.findAll { it.active }.size())
        println(permissions.findAll { it.active }*.name)
        permissions.findAll { it.active }.size() == 0
        (permissions.findAll { it.active }*.permissionGroupName) as Set == [] as Set
    }

}

package com.nls.myrewards

import com.nls.myrewards.client.ObjectMapperFactory
import com.nls.myrewards.util.MyRewardsStatefulPermissionGroup
import com.nls.myrewards.util.MyRewardsStatefulPermissionGroups
import spock.lang.Ignore

class UserGroupsIntegrationSpec extends BaseIntegrationSpec {

    def "I can list user groups"() {
        when:
        List<MyRewardsUserGroup> list = client.getUserGroups()
        list.each { println(ObjectMapperFactory.make().writeValueAsString(it)) }

        then:
        list.size() >= 4
        (list*.name as Set).containsAll(['unassigned', 'global admins', 'leavers'])

        when:
        MyRewardsUserGroup group = client.getUserGroup(list[0].id);

        then:
        group.id == list[0].id
    }


    def "I can get user group permissions"() {
        given:
        List<MyRewardsUserGroup> list = client.getUserGroups()

        when:
        MyRewardsUserGroup group = list.find { it.name == 'global admins' }
        MyRewardsStatefulPermissionGroups<MyRewardsUserGroupPermission> resources = new MyRewardsStatefulPermissionGroups<>(
                client.getUserGroupPermissions(group.id));

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
                client.getUserGroupPermissions(group.id));

        then:
        resources.activeGroupNames == [] as Set

        when:
        group = list.find { it.name == 'leavers' }
        resources = new MyRewardsStatefulPermissionGroups(
                client.getUserGroupPermissions(group.id));

        then:
        resources.activeGroupNames == [] as Set
    }

    @Ignore
    def "I can create a user group"() {
        when:
        MyRewardsUserGroup testingGroup = testingRootGroup;
        MyRewardsUserGroupRequest request = new MyRewardsUserGroupRequest()
            .withName("Test Sub Group")
            .withPosition(3)
            .withParentId(testingGroup.id)
            .withImageUrl("https://b2bm.s3.amazonaws.com/styles/panopoly_image_square/s3/sherpa_directory_logo.png?itok=EdxRJCBn")
        MyRewardsUserGroup group = client.createUserGroup(request)

        then:
        group.id > 0
        group.name == request.getName()
        group.position == 3
        group.parentId == testingGroup.id
        group.imageUrl != null
    }

    @Ignore
    def "I can assign permissions to the user group"() {
        given:
        MyRewardsUserGroup testingGroup = testingRootGroup;
        List<MyRewardsUserGroupPermission> permissions = client.getUserGroupPermissions(testingGroup.id)
        println(permissions.findAll { it.active }.size())
        println(permissions.findAll { it.active }*.name)

        when:
        permissions.each { it.withActive(true) }
        permissions = client.setUserGroupPermissions(testingGroup.id, permissions)

        then:
        println(permissions.findAll { it.active }.size())
        println(permissions.findAll { it.active }*.name)
        permissions.findAll { it.active }.size() == 4
        (permissions.findAll { it.active }*.permissionGroupName) as Set == ['General', 'Program in a Box'] as Set

        when:
        permissions.each { it.withActive(false) }
        permissions = client.setUserGroupPermissions(testingGroup.id, permissions)

        then:
        println(permissions.findAll { it.active }.size())
        println(permissions.findAll { it.active }*.name)
        permissions.findAll { it.active }.size() == 0
        (permissions.findAll { it.active }*.permissionGroupName) as Set == [] as Set
    }

    @Ignore
    def "Groups are sorted alphabetically if position is equal"() {
        when:
        MyRewardsUserGroup testingGroup = testingRootGroup;
        MyRewardsUserGroupRequest request = new MyRewardsUserGroupRequest()
                .withName("Test Group - Sort Order Test")
                .withParentId(testingGroup.id)
                .withPosition(99)

        MyRewardsUserGroup root = client.createUserGroup(request)
        request.withParentId(root.id)
        client.createUserGroup(request.withName("Group B").withPosition(2))
        client.createUserGroup(request.withName("Group A").withPosition(2))
        client.createUserGroup(request.withName("Group Y Upper").withPosition(1))
        client.createUserGroup(request.withName("Group X").withPosition(1))
        client.createUserGroup(request.withName("group y lower").withPosition(1))
        client.createUserGroup(request.withName("Group Z").withPosition(1))
        client.createUserGroup(request.withName("Group C").withPosition(2))
        List<MyRewardsUserGroup> list = client.getUserGroups()

        then:
        list.findAll { it.parentId == root.id }*.name ==
                ['Group X',
                'Group Y Upper',
                'group y lower',
                'Group Z',
                'Group A',
                'Group B',
                'Group C']
    }
}

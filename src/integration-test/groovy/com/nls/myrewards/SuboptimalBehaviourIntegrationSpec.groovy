package com.nls.myrewards

import com.nls.myrewards.client.ObjectMapperFactory
import spock.lang.Ignore

@Ignore
class SuboptimalBehaviourIntegrationSpec extends BaseIntegrationSpec {

    def "Groups are sorted alphabetically if position is equal"() {
        when:
        MyRewardsUserGroupRequest request = new MyRewardsUserGroupRequest()
                .withName("Test Group - Sort Order Test")
                .withParentId(testingRootGroup.id)
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

    def "If I change a user state to match the user group it should return to SameAsUserGroup"() {
        given:
        MyRewardsUserGroup group = expectTestUserGroup("Test User Group Permission Setting " + UUID.randomUUID().toString())
        List<MyRewardsUserGroupPermission> groupPermissions = client.getUserGroupPermissions(group.id)
        groupPermissions.each { println(ObjectMapperFactory.make().writeValueAsString(it)) }

        groupPermissions.each { it.withActive(true) }
        groupPermissions = client.setUserGroupPermissions(group.id, groupPermissions)
        groupPermissions.each { println(ObjectMapperFactory.make().writeValueAsString(it)) }

        MyRewardsUserRequest request = new MyRewardsUserRequest()
                .withEmail(getRandomEmail())
                .withTitle("Lord")
                .withFirstname("Higgledy")
                .withLastname("Piggledy")
                .withCompany(testingCompany, testingCompanyIdentifier)
                .withUserGroupId(group.id)
        MyRewardsUser user = client.createUser(request)

        when:
        List<MyRewardsUserPermission> userPermissions  = client.getUserPermissions(user.id)

        then:
        userPermissions.each { u ->
            assert u.active == groupPermissions.find {g ->  g.permissionGroupName == u.permissionGroupName && g.name == u.name }.active
            assert u.state == MyRewardsPermissionState.SameAsUserGroup
        }

        when:
        userPermissions.each { it.withActive(false) }
        userPermissions = client.setUserPermissions(user.id, userPermissions)

        then:
        userPermissions.each { u ->
            MyRewardsUserGroupPermission groupPermission = groupPermissions.find {g ->  g.permissionGroupName == u.permissionGroupName && g.name == u.name }
            if (u.active && groupPermission.active) {
                assert u.state == MyRewardsPermissionState.SameAsUserGroup
            } else {
                assert u.state == MyRewardsPermissionState.AlwaysDeny
            }
        }

        when:
        userPermissions.each { it.withActive(true) }
        userPermissions = client.setUserPermissions(user.id, userPermissions)

        then:
        userPermissions.each { u ->
            assert u.active == groupPermissions.find {g ->  g.permissionGroupName == u.permissionGroupName && g.name == u.name }.active
            assert u.state == MyRewardsPermissionState.SameAsUserGroup
        }
    }

    def "I should get a descriptive error for duplicate email"() {
        when:
        String email1 = getRandomEmail()
        MyRewardsUserRequest request = new MyRewardsUserRequest()
                .withEmail(email1)
                .withFirstname("Quigley")
                .withLastname("Aaron")
                .withCompany(testingCompany, testingCompanyIdentifier)
                .withUserGroupId(testingRootGroup.id)
        MyRewardsUser user = client.createUser(request)

        then:
        user.email == email1

        when:
        client.createUser(request)

        then:
        MyRewardsServerException e = thrown(MyRewardsServerException)
        e.error.message == "Validation failed: Email should be unique"
    }

    def "I should get a descriptive error for duplicate mobile"() {
        when:
        String mobile = getRandomPhone(0)
        MyRewardsUserRequest request = new MyRewardsUserRequest()
                .withEmail(email1)
                .withMobile(mobile)
                .withFirstname("Quigley")
                .withLastname("Aaron")
                .withCompany(testingCompany, testingCompanyIdentifier)
                .withUserGroupId(testingRootGroup.id)
        MyRewardsUser user = client.createUser(request)

        then:
        user.mobile == mobile

        when:
        client.createUser(request)

        then:
        MyRewardsServerException e = thrown(MyRewardsServerException)
        e.error.message == "Validation failed: Mobile should be unique"
    }

    def "I should get a descriptive error for duplicate username"() {
        when:
        String username = getRandomUsername()
        MyRewardsUserRequest request = new MyRewardsUserRequest()
                .withEmail(getRandomEmail())
                .withUsername(username)
                .withFirstname("Quigley")
                .withLastname("Aaron")
                .withCompany(testingCompany, testingCompanyIdentifier)
                .withUserGroupId(testingRootGroup.id)
        MyRewardsUser user = client.createUser(request)

        then:
        user.username == username

        when:
        client.createUser(request)

        then:
        MyRewardsServerException e = thrown(MyRewardsServerException)
        e.error.message == "Validation failed: Username should be unique"
    }
}

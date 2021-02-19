package com.nls.myrewards

import spock.lang.Ignore

class UserPermissionsIntegrationSpec extends BaseIntegrationSpec {
    @Ignore
    def "I can change user permissions and check state is relative to user group"() {
        given:
        MyRewardsUserGroup group = expectTestUserGroup("Test User Group Permission Setting")
        List<MyRewardsUserGroupPermission> groupPermissions = client.getUserGroupPermissions(group.id)
        groupPermissions.each { it.withActive(true) }
        groupPermissions = client.setUserGroupPermissions(group.id, groupPermissions)

        MyRewardsUserRequest request = new MyRewardsUserRequest()
                .withEmail("marc+${UUID.randomUUID().toString()}@sherpamarketing.co.uk")
                .withTitle("Mr")
                .withFirstname("Marc")
                .withLastname("Smith")
                .withCompany("Sherpa Marketing")
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

}

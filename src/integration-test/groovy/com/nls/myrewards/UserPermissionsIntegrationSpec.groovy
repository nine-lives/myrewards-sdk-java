package com.nls.myrewards

class UserPermissionsIntegrationSpec extends BaseIntegrationSpec {

    def "I can change user permissions and check state is relative to user group"() {
        given:
        MyRewardsUserGroup group = expectTestUserGroup("Test User Group Permission Setting " + UUID.randomUUID().toString())
        List<MyRewardsUserGroupPermission> groupPermissions = client.getUserGroupPermissions(group.id)
        groupPermissions.each { it.withActive(true) }
        groupPermissions = client.setUserGroupPermissions(group.id, groupPermissions)

        MyRewardsUserRequest request = new MyRewardsUserRequest()
                .withEmail(getRandomEmail())
                .withTitle("Lord")
                .withFirstname("Higgledy")
                .withLastname("Piggledy")
                .withCompany(testingCompany)
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
        !userPermissions.findAll({ it.active }).empty

        userPermissions.each { u ->
            // Possible to set user permissions that can't be set at group level
            //assert u.active == groupPermissions.find {g ->  g.permissionGroupName == u.permissionGroupName && g.name == u.name }.active

            // State we would like
            // assert u.state == MyRewardsPermissionState.SameAsUserGroup

            // Current behaviour
            assert u.state == MyRewardsPermissionState.AlwaysAllow
        }
    }

}

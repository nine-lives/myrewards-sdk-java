package com.nls.myrewards

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.nls.myrewards.client.ObjectMapperFactory
import spock.lang.Specification
import spock.lang.Unroll

class MyRewardsPermissionStateSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    @Unroll("I can get the correct state from the permission state string - #stateValue")
    def "I can get the correct state from the permission state string"() {
        when:
        MyRewardsPermissionState state = MyRewardsPermissionState.fromValue(stateValue)
        then:
        expected == state
        expected == null || stateValue == state.value

        where:
        stateValue           | expected
        "Always Allow"       | MyRewardsPermissionState.AlwaysAllow
        "Same As User Group" | MyRewardsPermissionState.SameAsUserGroup
        "Always Deny"        | MyRewardsPermissionState.AlwaysDeny
        ""                   | null
        null                 | null
    }

    @Unroll("I can deserialise the state - #stateValue")
    def "I can deserialise the state"() {
        when:
        Map<String, MyRewardsPermissionState> map = mapper.readValue(
                "{ \"state\": \"$stateValue\" }",
                new TypeReference<Map<String, MyRewardsPermissionState>>() {});
        MyRewardsPermissionState state = map.get("state");

        then:
        expected == state
        expected == null || stateValue == state.value

        where:
        stateValue           | expected
        "Always Allow"       | MyRewardsPermissionState.AlwaysAllow
        "Same As User Group" | MyRewardsPermissionState.SameAsUserGroup
        "Always Deny"        | MyRewardsPermissionState.AlwaysDeny
        ""                   | null
    }

    def "I can get an error for an invalid value"() {
        when:
        MyRewardsPermissionState state = MyRewardsPermissionState.fromValue("none")

        then:
        IllegalArgumentException exception = thrown(IllegalArgumentException)
        exception.message == 'Value not found for none'
    }


}

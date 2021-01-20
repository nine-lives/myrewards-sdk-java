package com.nls.myrewards

import spock.lang.Specification

class MyRewardsServerExceptionSpec extends Specification {

    def "I can construct the exception"() {
        given:
        MyRewardsError error = new MyRewardsError("error_message")

        when:
        MyRewardsServerException e = new MyRewardsServerException(401, 'Unauthorised', error)

        then:
        e.statusCode == 401
        e.statusMessage == 'Unauthorised'
        e.error.message == 'error_message'
    }
}

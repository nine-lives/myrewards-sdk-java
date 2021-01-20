package com.nls.myrewards

import spock.lang.Specification

class MyRewardsExceptionSpec extends Specification {

    def "I can create an exception with just a message"() {
        when:
        MyRewardsException e = new MyRewardsException("error message")

        then:
        e.message == "error message"
    }

    def "I can create an exception with just a cause"() {
        given:
        IllegalArgumentException cause = new IllegalArgumentException();
        when:
        MyRewardsException e = new MyRewardsException(cause)

        then:
        e.cause == cause
    }

}

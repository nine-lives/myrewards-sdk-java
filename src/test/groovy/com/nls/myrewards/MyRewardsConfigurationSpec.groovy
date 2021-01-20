package com.nls.myrewards

import spock.lang.Specification

class MyRewardsConfigurationSpec extends Specification {

    private String version

    def setup() {
        Properties versionProperties = new Properties()
        versionProperties.load(MyRewardsConfiguration.class.getClassLoader().getResourceAsStream("version.properties"))
        version = versionProperties.getProperty("version")
    }

    def "The defaults are the defaults"() {
        when:
        MyRewardsConfiguration config = new MyRewardsConfiguration()

        then:
        config.apiKey == null
        config.secretKey == null
        config.endpoint == 'https://staging.my-rewards.co.uk'
        config.maxConnectionsPerRoute == 20
        config.userAgent == "myrewards-java-sdk/${version}".toString()
        config.requestBurstSize == 20
        config.requestsPerSecond == 5
        !config.blockTillRateLimitReset
        version ==~ /1\.\d+\.\d+/
    }

    def "I can set configuration values"() {
        when:
        MyRewardsConfiguration config = new MyRewardsConfiguration()
            .withApiKey("secret")
            .withSecretKey("sekret")
            .withEndpoint("https://bpi.myrewards.com/")
            .withMaxConnectionsPerRoute(22)
            .withUserAgent("ninelives/9.0.0")
            .withBlockTillRateLimitReset(true)
            .withRequestBurstSize(25)
            .withRequestsPerSecond(10)

        then:
        config.apiKey == 'secret'
        config.secretKey == 'sekret'
        config.endpoint == 'https://bpi.myrewards.com/'
        config.maxConnectionsPerRoute == 22
        config.userAgent == "ninelives/9.0.0 myrewards-java-sdk/${version}".toString()
        config.requestBurstSize == 25
        config.requestsPerSecond == 10
        config.blockTillRateLimitReset
    }

    def "I can set and unset the user agent"() {
        when:
        MyRewardsConfiguration config = new MyRewardsConfiguration()

        then:
        config.userAgent == "myrewards-java-sdk/${version}".toString()

        when:
        config.withUserAgent("ninelives/9.0.0")

        then:
        config.userAgent == "ninelives/9.0.0 myrewards-java-sdk/${version}".toString()

        when:
        config.withUserAgent(null)

        then:
        config.userAgent == "myrewards-java-sdk/${version}".toString()

        when:
        config.withUserAgent(" ")

        then:
        config.userAgent == "myrewards-java-sdk/${version}".toString()
    }

}

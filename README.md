# My Rewards Client Java SDK
# My Rewards Client Java SDK

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.9ls/myrewards-java-sdk/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.9ls/myrewards-java-sdk)
[![Build Status](https://api.travis-ci.com/nine-lives/myrewards-sdk-java.png)](https://travis-ci.com/nine-lives/myrewards-sdk-java)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/91e4e97db854401d878fcf1670dfed25)](https://www.codacy.com/gh/nine-lives/myrewards-sdk-java/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=nine-lives/myrewards-sdk-java&amp;utm_campaign=Badge_Grade)
[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/91e4e97db854401d878fcf1670dfed25)](https://www.codacy.com/gh/nine-lives/myrewards-sdk-java/dashboard?utm_source=github.com&utm_medium=referral&utm_content=nine-lives/myrewards-sdk-java&utm_campaign=Badge_Coverage)

## Getting Started

The My Rewards API requires you to have an api key/token. 

All API calls are routed from the `MyRewards` object.

```
    MyRewards myrewards = MyRewards.make(apiKey);
```

The sdk is hosted on maven central so you can include it as a dependency 
in your projects as follows:

### Gradle/Grails
```
    compile 'com.9ls:myrewards-java-sdk:3.0.4'
```

### Apache Maven
```
    <dependency>
        <groupId>com.9ls</groupId>
        <artifactId>myrewards-java-sdk</artifactId>
        <version>3.0.4</version>
    </dependency>
```

### Apache Ivy
```
    <dependency org="com.9ls" name="myrewards-java-sdk" rev="3.0.4" />
```

## Custom Configuration

You can also use `ClientConfiguration` to configure the SDK. Apart
from the the api key all the other values have defaults.

```
    MyRewards myrewards = MyRewards.make(new Configuration()
        .withApiKey(apiKey)
        .withSecretKey(secretKey)
        .withEndpoint("https://api.my-rewards.co.uk")
        .withMaxConnectionsPerRoute(20)
        .withUserAgent("myrewards-sdk-java 3.0.4")
        .withBlockTillRateLimitReset(false)
        .withRequestsPerSecond(5)
        .withRequestBurstSize(20);
```

| Configuration Attribute | Description |
| ----------------------- | ----------- |
| Endpoint | The base api url. Defaults to https://staging.my-rewards.co.uk |
| MaxConnectionsPerRoute | The effective maximum number of concurrent connections in the pool. Connections try to make use of the keep-alive directive. Defaults to 20
| UserAgent | The user agent string sent in the request
| BlockTillRateLimitReset | If set to true then the client will block if the rate limit has been reached until the reset timestamp has expired. Defaults to false
| RequestsPerSecond | If rate limited is true then the maximum requests per second 
| RequestBurstSize | If rate limited the number of consecutive requests allowed before rate limit is enforced 


## Build

Once you have checked out the project you can build and test the project with the following command:

```
    gradlew check -x integrationTest -x jacocoTestReport
```

 
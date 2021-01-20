# My Rewards Client Java SDK

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.9ls/myrewards-java-sdk/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.9ls/myrewards-java-sdk)
[![Build Status](https://api.travis-ci.org/nine-lives/myrewards-sdk-java.png)](https://travis-ci.org/nine-lives/myrewards-sdk-java)
[![Code Quality](https://api.codacy.com/project/badge/grade/d289b210b4b94dc69384622a5732bb05)](https://www.codacy.com/app/nine-lives/myrewards-sdk-java)
[![Coverage](https://api.codacy.com/project/badge/coverage/d289b210b4b94dc69384622a5732bb05)](https://www.codacy.com/app/nine-lives/myrewards-sdk-java)

## Getting Started

The My Rewards API requires you to have an api key/token. 

All API calls are routed from the `My Rewards` object.

```
    My Rewards myrewards = My Rewards.make(apiKey);
```

The sdk is hosted on maven central so you can include it as a dependency 
in your projects as follows:

### Gradle/Grails
```
    compile 'com.9ls:myrewards-java-sdk:1.0.0'
```

### Apache Maven
```
    <dependency>
        <groupId>com.9ls</groupId>
        <artifactId>myrewards-java-sdk</artifactId>
        <version>1.0.0</version>
    </dependency>
```

### Apache Ivy
```
    <dependency org="com.9ls" name="myrewards-java-sdk" rev="1.0.0" />
```

## Create a Recipient

To create a recipient:

```
        Recipient recipient = myrewards.createRecipient(Recipient.builder()
            .withName("John Doe")
            .withAccountNo("12345678")
            .withSortCode("123456")
            .withCurrencyCode("GBP")
            .withLegalType(LegalType.PRIVATE)
            .build());
```

## Execute a Bank Transfer

To create a recipient:

```
        Transaction transaction = myrewards.transfer(BankTransfer
            .withBankDetails("Integeration Spec", "12345678", "123456", LegalType.PRIVATE)
            .withCurrencyCode("GBP")
            .withAccountId("my-account-id")
            .withAmount(1200)
            .withIdempotentKey("my-idempotent-key)
            .withReconciliation("my-reconcilation-key")
            .withReference("my-reference")
            .withTag("my-tag")
            .build());
```

## Custom Configuration

You can also use `ClientConfiguration` to configure the SDK. Apart
from the the api key all the other values have defaults.

```
    My Rewards myrewards = My Rewards.make(new Configuration()
        .withApiKey(apiKey)
        .withEndpoint("https://api.myrewards.com")
        .withMaxConnectionsPerRoute(20)
        .withUserAgent("myrewards-sdk-java 1.0.0")
        .withBlockTillRateLimitReset(false)
        .withRequestsPerSecond(5)
        .withRequestBurstSize(20);
```

| Configuration Attribute | Description |
| ----------------------- | ----------- |
| Endpoint | The base api url. Defaults to https://api.myrewards.com |
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

 
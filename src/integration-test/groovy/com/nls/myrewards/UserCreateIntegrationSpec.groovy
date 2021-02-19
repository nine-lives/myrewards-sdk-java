package com.nls.myrewards

import com.nls.myrewards.client.ObjectMapperFactory
import org.joda.time.LocalDate
import spock.lang.Ignore

class UserCreateIntegrationSpec extends BaseIntegrationSpec {
    @Ignore
    def "I create marc@9ls.com as an admin user"() {
        when:
        MyRewardsUserRequest request = new MyRewardsUserRequest()
        //.withUsername("bwayne")
                .withEmail("marc@9ls.com")
        //.withTitle("Mr")
                .withFirstname("Marc")
                .withLastname("Smith")
                .withCompany("Sherpa Marketing")
        //.withJobTitle("CTO")
        //.withAddress1("Wayne Manor")
        //.withAddress2("1007 Mountain Drive")
        //.withTown("Gotham")
        //.withPostcode("G1 1BM")
        //.withCounty("New Jersey")
        //.withCountry('United States')
        //.withDateOfBirth(LocalDate.parse("1980-02-19"))
        //.withTelephone("+447876543210")
        //.withMobile("+447765432101")
        //.withTsandcs(true)
        //.withConsented(true)
        //.withMarketingConsented(true)
                .withUserGroupId(15607)
        //.withRegistrationAnswersAttributes([new MyRewardsRegistrationAnswerAttribute(2, "Because I'm Batman")])
        //.addRegistrationAnswersAttribute(new MyRewardsRegistrationAnswerAttribute(16, "Alfred"))
        MyRewardsUser user = client.createUser(request)
        println(ObjectMapperFactory.make().writeValueAsString(user))

        then:
        user != null
        user.id > 0
        user.email == 'marc@9ls.com'
        user.firstname == 'Marc'
        user.lastname == 'Smith'
        user.userGroupId == 15607
        user.company == 'Sherpa Marketing'
    }

    @Ignore
    def "I get an error when I try to create a user with an existing email address"() {
        when:
        MyRewardsUserRequest request = new MyRewardsUserRequest()
                .withEmail("marc@9ls.com")
                .withFirstname("Marc")
                .withLastname("Smith")
                .withCompany("Sherpa Marketing")
                .withUserGroupId(15607)
        client.createUser(request)

        then:
        MyRewardsServerException e = thrown(MyRewardsServerException)
        e.statusCode == 422
        e.message.startsWith('422: Unprocessable Entity')
    }

    def "I can an error when I try to create a user without mandatory fields"() {
        when:
        MyRewardsUserRequest request = new MyRewardsUserRequest()
        client.createUser(request)

        then:
        MyRewardsServerException e = thrown(MyRewardsServerException)
        e.statusCode == 422
        e.message.startsWith('422: Unprocessable Entity')
    }

    def "I can get a user's details by doing an empty update"() {
        when:
        MyRewardsUserRequest request = new MyRewardsUserRequest()
                .withUsername("bwayne")
                .withEmail("marc+${UUID.randomUUID().toString()}@9ls.com")
                .withTitle("Mr")
                .withFirstname("Marc")
                .withLastname("Smith")
                .withCompany("Sherpa Marketing")
                .withJobTitle("CTO")
                .withAddress1("Wayne Manor")
                .withAddress2("1007 Mountain Drive")
                .withTown("Gotham")
                .withPostcode("G1 1BM")
                .withCounty("New Jersey")
                .withCountry('UK')
                .withDateOfBirth(LocalDate.parse("1980-02-19"))
                .withTelephone("+447876543210")
                .withMobile("+447765432101")
                .withTsandcs(true)
                .withConsented(true)
                .withMarketingConsented(true)
                .withUserGroupId(testingRootGroup.id)
                .withRegistrationAnswersAttributes([new MyRewardsRegistrationAnswerAttribute(10703, "Gold")])
                //.withRegistrationAnswersAttributes([new MyRewardsRegistrationAnswerAttribute(10606, "Sherpa Marketing")])
        //.addRegistrationAnswersAttribute(new MyRewardsRegistrationAnswerAttribute(16, "Alfred"))
        MyRewardsUser user = client.createUser(request)
        println(ObjectMapperFactory.make().writeValueAsString(user))

        then:
        user != null
        user.id > 0

        when:
        MyRewardsUserRequest getRequest = new MyRewardsUserRequest();
        MyRewardsUser updateUser = client.updateUser(user.id, getRequest)
        println(ObjectMapperFactory.make().writeValueAsString(updateUser))

        then:
        updateUser.id == user.id
        updateUser.username == user.username
        updateUser.email == user.email
        updateUser.title == user.title
        updateUser.firstname == user.firstname
        updateUser.lastname == user.lastname
        updateUser.jobTitle == user.jobTitle
        updateUser.address1 == user.address1
        updateUser.address2 == user.address2
        updateUser.town == user.town
        updateUser.postcode == user.postcode
        updateUser.county == user.county
        updateUser.country == user.country
        updateUser.dateOfBirth == user.dateOfBirth
        updateUser.telephone == user.telephone
        updateUser.mobile == user.mobile
        updateUser.tsandcs == user.tsandcs
        updateUser.consented == user.consented
        updateUser.marketingConsented == user.marketingConsented
        updateUser.userGroupId == user.userGroupId
    }

    def "I can get move a user from one group to another"() {
        when:
        MyRewardsUserRequest request = new MyRewardsUserRequest()
                .withEmail("marc+${UUID.randomUUID().toString()}@9ls.com")
                .withTitle("Mr")
                .withFirstname("Test")
                .withLastname("User")
                .withCompany("Sherpa Marketing")
                .withUserGroupId(testingRootGroup.id)
        MyRewardsUser user = client.createUser(request)

        then:
        user.id > 0
        user.userGroupId == testingRootGroup.id

        when:
        MyRewardsUserGroup newGroup = client.createUserGroup(new MyRewardsUserGroupRequest()
            .withParentId(testingRootGroup.id)
            .withPosition(1)
            .withName("Move user group test"))

        MyRewardsUser movedUser = client.updateUser(user.id, new MyRewardsUserRequest()
            .withUserGroupId(newGroup.id))

        then:
        movedUser.id == user.id
        movedUser.userGroupId == newGroup.id
    }

}

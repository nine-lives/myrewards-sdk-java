package com.nls.myrewards

import com.nls.myrewards.client.ObjectMapperFactory
import spock.lang.Ignore

@Ignore
class UserCreateIntegrationSpec extends BaseIntegrationSpec {
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


//    def "I create marc@9ls.com as an admin user"() {
//        when:
//        MyRewardsUserRequest request = new MyRewardsUserRequest()
//        //.withUsername("bwayne")
//                .withEmail("marc@9ls.com")
//        //.withTitle("Mr")
//                .withFirstname("Marc")
//                .withLastname("Smith")
//                .withCompany("Sherpa Marketing")
//        //.withJobTitle("CTO")
//        //.withAddress1("Wayne Manor")
//        //.withAddress2("1007 Mountain Drive")
//        //.withTown("Gotham")
//        //.withPostcode("G1 1BM")
//        //.withCounty("New Jersey")
//        //.withCountry('United States')
//        //.withDateOfBirth(LocalDate.parse("1980-02-19"))
//        //.withTelephone("+447876543210")
//        //.withMobile("+447765432101")
//        //.withTsandcs(true)
//        //.withConsented(true)
//        //.withMarketingConsented(true)
//                .withUserGroupId(15607)
//        //.withRegistrationAnswersAttributes([new MyRewardsRegistrationAnswerAttribute(2, "Because I'm Batman")])
//        //.addRegistrationAnswersAttribute(new MyRewardsRegistrationAnswerAttribute(16, "Alfred"))
//        MyRewardsUser user = client.createUser(request)
//        println(ObjectMapperFactory.make().writeValueAsString(user))
//
//        then:
//        user != null
//        user.id > 0
//        user.email == 'marc@9ls.com'
//        user.firstname == 'Marc'
//        user.lastname == 'Smith'
//        user.userGroupId == 15607
//        user.company == 'Sherpa Marketing'
//    }

}

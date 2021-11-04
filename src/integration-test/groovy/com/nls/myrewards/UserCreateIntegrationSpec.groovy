package com.nls.myrewards

import com.nls.myrewards.client.ObjectMapperFactory
import org.joda.time.LocalDate

class UserCreateIntegrationSpec extends BaseIntegrationSpec {

    def "I get an error when I try to create a user with an existing email address"() {
        when:
        String email1 = getRandomEmail()
        MyRewardsUserRequest request = new MyRewardsUserRequest()
                .withEmail(email1)
                .withFirstname("Quigley")
                .withLastname("Aaron")
                .withCompany(testingCompany, testingCompanyIdentifier)
                .withUserGroupId(testingRootGroup.id)
        MyRewardsUser user = client.createUser(request)

        then:
        user.email == email1

        when:
        String email2 = getRandomEmail()
        user = client.createUser(request.withEmail(email2))

        then:
        user.email == email2

        when:
        client.createUser(request.withEmail(email1))

        then:
        MyRewardsServerException e = thrown(MyRewardsServerException)
        e.statusCode == 422
        e.message.startsWith('422: Unprocessable Entity')
    }

    def "I get an error when I try to create a user with an existing mobile number"() {
        when:
        String phone1 = getRandomPhone(0)
        MyRewardsUserRequest request = new MyRewardsUserRequest()
                .withEmail(getRandomEmail())
                .withMobile(phone1)
                .withFirstname("Quigley")
                .withLastname("Aaron")
                .withCompany(testingCompany, testingCompanyIdentifier)
                .withUserGroupId(testingRootGroup.id)
        MyRewardsUser user = client.createUser(request)

        then:
        user.mobile == phone1

        when:
        String phone2 = getRandomPhone(1)
        user = client.createUser(request.withMobile(phone2).withEmail(getRandomEmail()))

        then:
        user.mobile == phone2

        when:
        client.createUser(request.withMobile(phone1).withEmail(getRandomEmail()))

        then:
        MyRewardsServerException e = thrown(MyRewardsServerException)
        e.statusCode == 422
        e.message.startsWith('422: Unprocessable Entity')
    }

    def "I get an error when I try to create a user with an existing username"() {
        when:
        String username1 = getRandomUsername()
        MyRewardsUserRequest request = new MyRewardsUserRequest()
                .withEmail(getRandomEmail())
                .withUsername(username1)
                .withFirstname("Quigley")
                .withLastname("Aaron")
                .withCompany(testingCompany, testingCompanyIdentifier)
                .withUserGroupId(testingRootGroup.id)
        MyRewardsUser user = client.createUser(request)

        then:
        user.username == username1

        when:
        String username2 = getRandomUsername()
        user = client.createUser(request.withUsername(username2).withEmail(getRandomEmail()))

        then:
        user.username == username2

        when:
        client.createUser(request.withUsername(username1).withEmail(getRandomEmail()))

        then:
        MyRewardsServerException e = thrown(MyRewardsServerException)
        e.statusCode == 422
        e.message.startsWith('422: Unprocessable Entity')
    }

    def "I can create a user with all other fields duplicated except for phone, mobile and username"() {
        when:
        String username1 = getRandomUsername()
        MyRewardsUserRequest request = new MyRewardsUserRequest()
                .withUsername(username1)
                .withEmail(getRandomEmail())
                .withTitle("Mr")
                .withFirstname("Igor")
                .withLastname("Frankfurter")
                .withCompany(testingCompany, testingCompanyIdentifier)
                .withJobTitle("CTO")
                .withAddress1("Wayne Manor")
                .withAddress2("1007 Mountain Drive")
                .withTown("Gotham")
                .withPostcode("G1 1BM")
                .withCounty("New Jersey")
                .withCountry('UK')
                .withDateOfBirth(LocalDate.parse("1980-02-19"))
                .withTelephone("+447876543210")
                .withMobile(getRandomPhone(2))
                .withTsandcs(true)
                .withConsented(true)
                .withMarketingConsented(true)
                .withUserGroupId(testingRootGroup.id)
                .withRegistrationAnswersAttributes([new MyRewardsRegistrationAnswerAttribute(10703, "Gold")])
        MyRewardsUser user = client.createUser(request)

        then:
        user.username == username1

        when:
        String username2 = getRandomUsername()
        user = client.createUser(request
                .withUsername(username2)
                .withEmail(getRandomEmail())
                .withMobile(getRandomPhone(4)))

        then:
        user.username == username2
    }

    def "I get an error when I try to create a user without mandatory fields"() {
        when:
        MyRewardsUserRequest request = new MyRewardsUserRequest()
        client.createUser(request)

        then:
        MyRewardsServerException e = thrown(MyRewardsServerException)
        e.statusCode == 422
        e.message.startsWith('422: Unprocessable Entity')
    }

    def "I can get a user's details"() {
        when:
        MyRewardsUserRequest request = new MyRewardsUserRequest()
                .withUsername(getRandomUsername())
                .withEmail(getRandomEmail())
                .withTitle("Mr")
                .withFirstname("Joel")
                .withLastname("Gooden")
                .withCompany(testingCompany, testingCompanyIdentifier)
                .withJobTitle("CTO")
                .withAddress1("Wayne Manor")
                .withAddress2("1007 Mountain Drive")
                .withTown("Gotham")
                .withPostcode("G1 1BM")
                .withCounty("New Jersey")
                .withCountry('UK')
                .withDateOfBirth(LocalDate.parse("1980-02-19"))
                .withTelephone("+447876543210")
                .withMobile(getRandomPhone(5))
                .withTsandcs(true)
                .withConsented(true)
                .withMarketingConsented(true)
                .withUserGroupId(testingRootGroup.id)

        MyRewardsUser user = client.createUser(request)
        //println(ObjectMapperFactory.make().writeValueAsString(user))

        then:
        user != null
        user.id > 0

        when:
        MyRewardsUser updateUser = client.getUser(user.id)
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
        //updateUser.marketingConsented == user.marketingConsented
        updateUser.userGroupId == user.userGroupId
    }

    def "I can move a user from one group to another"() {
        when:
        MyRewardsUserRequest request = new MyRewardsUserRequest()
                .withEmail(getRandomEmail())
                .withTitle("Mr")
                .withFirstname("Test")
                .withLastname("User")
                .withCompany(testingCompany, testingCompanyIdentifier)
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

    def "I can find a user"() {
        given:
        int start = 506678
        String email = 'marc@9ls.com'
    }
}

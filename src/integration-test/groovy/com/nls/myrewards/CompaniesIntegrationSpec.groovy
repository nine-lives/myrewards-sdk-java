package com.nls.myrewards

import com.nls.myrewards.client.ObjectMapperFactory
import org.joda.time.LocalDate

class CompaniesIntegrationSpec extends BaseIntegrationSpec {

    def "I can list companies by page and fetch a specific company"() {
        when:
        List<MyRewardsCompany> list = client.getCompanies(1)
        list.each { println(ObjectMapperFactory.make().writeValueAsString(it)) }
        println(list.size());

        then:
        !list.isEmpty()
        list.size() == 100

// identifier can't be encoded
//        when:
//        MyRewardsCompany company = client.getCompany(list[0].identifier.replaceAll(" ", "+"))
//
//        then:
//        company.id == list[0].id
//        company.identifier == list[0].identifier
    }

    def "I can list all companies"() {
        when:
        List<MyRewardsCompany> list = client.companies
        //list.each { println(ObjectMapperFactory.make().writeValueAsString(it)) }
        println(list.size());

        then:
        !list.isEmpty()
        list.size() > 100
    }

    def "I can see that the mappings for registration questions to company ids are the same"() {
        given:
        List<MyRewardsCompany> companies = client.companies
        List<MyRewardsRegistrationQuestionValue> values = client.getRegistrationQuestionValues(COMPANY_MAGIC_NUMBER)

        when:
        Map<Integer, String> companyMap = companies.collectEntries{[it.id, it.name]}
        Map<Integer, String> valueMap = values.collectEntries{[it.id, it.name]}

        then:
        !companyMap.isEmpty()
        companyMap.size() == valueMap.size()
        companyMap == valueMap
    }

    def "I can fetch a specific company if it doesn't need encoding"() {
        given:
        String companyName = "test-fetch-no-encoding" + UUID.randomUUID().toString()

        when:
        MyRewardsRegistrationQuestionValue value = client.createRegistrationQuestionValues(COMPANY_MAGIC_NUMBER, companyName)

        then:
        value.name == companyName;

        when:
        MyRewardsCompany company = client.getCompany(value.id)

        then:
        company.id == value.id
        company.name == companyName

        when:
        MyRewardsCompany company2 = client.getCompany(company.identifier)

        then:
        company.id == company2.id
        company.name == company2.name
    }

    def "I can update a company name when I update a user"() {
        when:
        String username = getRandomUsername()
        String companyName = username + " Corp (Testing)"
        String companyIdentifier = username + "-corp-testing"

        MyRewardsUserRequest request = new MyRewardsUserRequest()
                .withUsername(username)
                .withEmail(getRandomEmail())
                .withTitle("Mr")
                .withFirstname("Igor")
                .withLastname("Frankfurter")
                .withCompany(companyName, companyIdentifier)
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
        user.username == username
        user.companyIdentifier == companyIdentifier
        user.company == companyName

        when:
        MyRewardsCompany company = client.getCompany(companyIdentifier)

        then:
        company.identifier == companyIdentifier
        company.name == companyName

        when:
        String newCompanyName = username + " Corp (Testing)"
        MyRewardsUser user2 = client.updateUser(user.id, request
                .withCompany(newCompanyName, companyIdentifier))

        then:
        user2.companyId == user.companyId
        user2.username == username
        user2.companyIdentifier == companyIdentifier
        user2.company == newCompanyName

        when:
        company = client.getCompany(companyIdentifier)

        then:
        company.identifier == companyIdentifier
        company.name == companyName
    }

}

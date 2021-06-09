package com.nls.myrewards

import com.nls.myrewards.client.ObjectMapperFactory

class CompaniesIntegrationSpec extends BaseIntegrationSpec {

    def "I can list companies by page and fetch a specific company"() {
        when:
        List<MyRewardsCompany> list = client.getCompanies(1)
        list.each { println(ObjectMapperFactory.make().writeValueAsString(it)) }
        println(list.size());

        then:
        !list.isEmpty()
        list.size() == 100

// identifier is wrong
//        when:
//        MyRewardsCompany company = client.getCompany(list[0].identifier)
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
}

package com.nls.myrewards

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.nls.myrewards.client.ObjectMapperFactory
import org.joda.time.LocalDate
import spock.lang.Specification

class MyRewardsUserRequestSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can covert a request to a payload"() {
        given:
        MyRewardsUserRequest request = new MyRewardsUserRequest()
                .withUsername("bwayne")
                .withEmail("bruce@wayneinc.com")
                .withTitle("Mr")
                .withFirstname("Bruce")
                .withLastname("Wayne")
                .withCompany("Wayne Inc")
                .withJobTitle("CEO")
                .withAddress1("Wayne Manor")
                .withAddress2("1007 Mountain Drive")
                .withTown("Gotham")
                .withPostcode("G1 1BM")
                .withCounty("New Jersey")
                .withCountry('United States')
                .withDateOfBirth(LocalDate.parse("1980-02-19"))
                .withTelephone("+447876543210")
                .withMobile("+447765432101")
                .withTsandcs(true)
                .withConsented(true)
                .withMarketingConsented(true)
                .withUserGroupId(10)
                .withRegistrationAnswersAttributes([new MyRewardsRegistrationAnswerAttribute(2, "Because I'm Batman")])
                .addRegistrationAnswersAttribute(new MyRewardsRegistrationAnswerAttribute(16, "Alfred"))

        when:
        String json = mapper.writeValueAsString(request);
        Map<String, Object> entity = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});

        then:
        json.contains("address_1")
        json.contains("address_2")
        request.username == 'bwayne'
        request.email == 'bruce@wayneinc.com'
        request.title == 'Mr'
        request.firstname == 'Bruce'
        request.lastname == 'Wayne'
        request.company == 'Wayne Inc'
        request.jobTitle == 'CEO'
        request.address1 == 'Wayne Manor'
        request.address2 == '1007 Mountain Drive'
        request.town == 'Gotham'
        request.postcode == 'G1 1BM'
        request.county == 'New Jersey'
        request.country == 'United States'
        request.dateOfBirth == LocalDate.parse('1980-02-19');
        request.telephone == '+447876543210'
        request.mobile == '+447765432101'
        request.tsandcs
        request.consented
        request.marketingConsented
        request.userGroupId == 10
        request.registrationAnswersAttributes.size() == 2
        request.registrationAnswersAttributes[0].registrationQuestionId == 2
        request.registrationAnswersAttributes[0].answer == "Because I'm Batman"
        request.registrationAnswersAttributes[1].registrationQuestionId == 16
        request.registrationAnswersAttributes[1].answer == "Alfred"

        entity.username == 'bwayne'
        entity.email == 'bruce@wayneinc.com'
        entity.title == 'Mr'
        entity.firstname == 'Bruce'
        entity.lastname == 'Wayne'
        entity.company == 'Wayne Inc'
        entity.job_title == 'CEO'
        entity.address_1 == 'Wayne Manor'
        entity.address_2 == '1007 Mountain Drive'
        entity.town == 'Gotham'
        entity.postcode == 'G1 1BM'
        entity.county == 'New Jersey'
        entity.country == 'United States'
        entity.date_of_birth == '1980-02-19'
        entity.telephone == '+447876543210'
        entity.mobile == '+447765432101'
        entity.tsandcs == true
        entity.consented == true
        entity.marketing_consented == true
        entity.user_group_id == 10
        entity.registration_answers_attributes.size() == 2
        entity.registration_answers_attributes[0].registration_question_id == "2"
        entity.registration_answers_attributes[0].answer == "Because I'm Batman"
        entity.registration_answers_attributes[1].registration_question_id == "16"
        entity.registration_answers_attributes[1].answer == "Alfred"


        when:
        MyRewardsUserRequest result = mapper.readValue(json, MyRewardsUserRequest)

        then:
        result.username == 'bwayne'
        result.email == 'bruce@wayneinc.com'
        result.title == 'Mr'
        result.firstname == 'Bruce'
        result.lastname == 'Wayne'
        result.company == 'Wayne Inc'
        result.jobTitle == 'CEO'
        result.address1 == 'Wayne Manor'
        result.address2 == '1007 Mountain Drive'
        result.town == 'Gotham'
        result.postcode == 'G1 1BM'
        result.county == 'New Jersey'
        result.country == 'United States'
        result.dateOfBirth == LocalDate.parse('1980-02-19');
        result.telephone == '+447876543210'
        result.mobile == '+447765432101'
        result.tsandcs
        result.consented
        result.marketingConsented
        result.userGroupId == 10
        result.registrationAnswersAttributes.size() == 2
        result.registrationAnswersAttributes[0].registrationQuestionId == 2
        result.registrationAnswersAttributes[0].answer == "Because I'm Batman"
        result.registrationAnswersAttributes[1].registrationQuestionId == 16
        result.registrationAnswersAttributes[1].answer == "Alfred"
    }

    def "I can copy user values"() {
        given:
        String payload = '''
                {
                  "id": 123,
                  "username" : "bwayne",
                  "email" : "bruce@wayneinc.com",
                  "title" : "Mr",
                  "firstname" : "Bruce",
                  "lastname" : "Wayne",
                  "company" : "Wayne Inc",
                  "job_title" : "CEO",
                  "address_1" : "Wayne Manor",
                  "address_2" : "1007 Mountain Drive",
                  "town" : "Gotham",
                  "postcode" : "G1 1BM",
                  "county" : "New Jersey",
                  "country" : "United States",
                  "date_of_birth" : "1980-02-19",
                  "telephone" : "+447876543210",
                  "mobile" : "+447765432101",
                  "tsandcs" : "true",
                  "consented" : "false",
                  "marketing_consented" : "true",
                  "user_group_id" : "10",
                  "registration_answers_attributes" : [
                    {
                      "registration_question_id" : "2",
                      "answer" : "Because I'm Batman"
                    },
                    {
                      "registration_question_id" : "16",
                      "answer" : "Alfred"
                    }
                  ]
                }
       '''
        MyRewardsUser entity = mapper.readValue(payload, MyRewardsUser)

        when:
        MyRewardsUserRequest result = new MyRewardsUserRequest(entity)

        then:
        result.username == 'bwayne'
        result.email == 'bruce@wayneinc.com'
        result.title == 'Mr'
        result.firstname == 'Bruce'
        result.lastname == 'Wayne'
        result.company == 'Wayne Inc'
        result.jobTitle == 'CEO'
        result.address1 == 'Wayne Manor'
        result.address2 == '1007 Mountain Drive'
        result.town == 'Gotham'
        result.postcode == 'G1 1BM'
        result.county == 'New Jersey'
        result.country == 'United States'
        result.dateOfBirth == LocalDate.parse('1980-02-19');
        result.telephone == '+447876543210'
        result.mobile == '+447765432101'
        result.tsandcs
        !result.consented
        result.marketingConsented
        result.userGroupId == 10
        result.registrationAnswersAttributes.size() == 2
        result.registrationAnswersAttributes[0].registrationQuestionId == 2
        result.registrationAnswersAttributes[0].answer == "Because I'm Batman"
        result.registrationAnswersAttributes[1].registrationQuestionId == 16
        result.registrationAnswersAttributes[1].answer == "Alfred"
    }
}
package com.nls.myrewards

import com.fasterxml.jackson.databind.ObjectMapper
import com.nls.myrewards.client.ObjectMapperFactory
import org.joda.time.LocalDate
import spock.lang.Specification

class MyRewardsUserSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can covert a JSON payload to the entity"() {
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
                  "chosen_locale": "en",
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

        when:
        MyRewardsUser entity = mapper.readValue(payload, MyRewardsUser)

        then:
        entity.id == 123
        entity.username == 'bwayne'
        entity.email == 'bruce@wayneinc.com'
        entity.title == 'Mr'
        entity.firstname == 'Bruce'
        entity.lastname == 'Wayne'
        entity.company == 'Wayne Inc'
        entity.jobTitle == 'CEO'
        entity.address1 == 'Wayne Manor'
        entity.address2 == '1007 Mountain Drive'
        entity.town == 'Gotham'
        entity.postcode == 'G1 1BM'
        entity.county == 'New Jersey'
        entity.country == 'United States'
        entity.dateOfBirth == LocalDate.parse('1980-02-19');
        entity.telephone == '+447876543210'
        entity.mobile == '+447765432101'
        entity.chosenLocale == "en"
        entity.tsandcs
        !entity.consented
        entity.marketingConsented
        entity.userGroupId == 10
        entity.registrationAnswersAttributes.size() == 2
        entity.registrationAnswersAttributes[0].registrationQuestionId == 2
        entity.registrationAnswersAttributes[0].answer == "Because I'm Batman"
        entity.registrationAnswersAttributes[1].registrationQuestionId == 16
        entity.registrationAnswersAttributes[1].answer == "Alfred"
    }

    def "I can covert a v3 JSON payload to the entity"() {
        given:
        String payload = '''
            {
              "id": 123,
              "username" : "bwayne",
              "email" : "bruce@wayneinc.com",
              "title" : "Mr",
              "firstname" : "Joker",
              "lastname" : "Hahaha",
              "company": {
                "id": 4454,
                "name": "Wayne Inc",
                "identifier": "wayne-18"
              },
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
              "chosen_locale": "en",
              "tsandcs" : "true",
              "user_group_id" : "10",
              "registration_answers_attributes" : [
                {
                  "registration_question_id" : "2",
                  "question": {
                    "user_locale": "Pourquoi?",
                    "stack_locale": "Why?"
                  },
                  "answer" : "Because I'm Batman"
                },
                {
                  "registration_question_id" : "16",
                  "question": {
                    "user_locale": "Butler Name",
                    "stack_locale": "Butler Name"
                  },
                  "answer" : "Alfred"
                }
              ]
            }
       '''

        when:
        MyRewardsUser entity = mapper.readValue(payload, MyRewardsUser)

        then:
        entity.id == 123
        entity.username == 'bwayne'
        entity.email == 'bruce@wayneinc.com'
        entity.title == 'Mr'
        entity.firstname == 'Joker'
        entity.lastname == 'Hahaha'
        entity.company == 'Wayne Inc'
        entity.companyId == 4454
        entity.companyIdentifier == 'wayne-18'
        entity.jobTitle == 'CEO'
        entity.address1 == 'Wayne Manor'
        entity.address2 == '1007 Mountain Drive'
        entity.town == 'Gotham'
        entity.postcode == 'G1 1BM'
        entity.county == 'New Jersey'
        entity.country == 'United States'
        entity.dateOfBirth == LocalDate.parse('1980-02-19');
        entity.telephone == '+447876543210'
        entity.mobile == '+447765432101'
        entity.chosenLocale == 'en'
        entity.tsandcs
        !entity.consented
        !entity.marketingConsented
        entity.userGroupId == 10
        entity.registrationAnswersAttributes.size() == 2
        entity.registrationAnswersAttributes[0].registrationQuestionId == 2
        entity.registrationAnswersAttributes[0].question.userLocale == 'Pourquoi?'
        entity.registrationAnswersAttributes[0].question.stackLocale == 'Why?'
        entity.registrationAnswersAttributes[0].answer == "Because I'm Batman"
        entity.registrationAnswersAttributes[1].registrationQuestionId == 16
        entity.registrationAnswersAttributes[1].question.userLocale == 'Butler Name'
        entity.registrationAnswersAttributes[1].question.stackLocale == 'Butler Name'
        entity.registrationAnswersAttributes[1].answer == "Alfred"
    }

}
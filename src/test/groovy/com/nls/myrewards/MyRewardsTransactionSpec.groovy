package com.nls.myrewards

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.nls.myrewards.util.ObjectMapperFactory
import org.joda.time.DateTime
import spock.lang.Specification

class MyRewardsTransactionSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
            {
                "id": 123,
                "user_id" : 124,
                "description" : "transactions",
                "value" : 100,
                "balance" : 100,
                "transaction_type" : "programme points",
                "remote_transaction_id": 999,
                "created_at" : "2016-03-18T02:20:02.000+00:00"
            }
       '''

        when:
        MyRewardsTransaction entity = mapper.readValue(payload, MyRewardsTransaction.class)

        then:
        entity.id == 123
        entity.userId == 124
        entity.value == 100
        entity.balance == 100
        entity.transactionType == 'programme points'
        entity.remoteTransactionId == 999
        entity.createdAt == DateTime.parse("2016-03-18T02:20:02.000+00:00")
    }

    def "I can covert a list JSON payload to the entity"() {
        given:
        String payload = '''
            [
                {
                    "id": 123,
                    "user_id" : 124,
                    "description" : "transactions",
                    "value" : 100,
                    "balance" : 100,
                    "transaction_type" : "programme points",
                    "remote_transaction_id": 999,
                    "created_at" : "2016-03-18T02:20:02.000+00:00"
                },
                {
                    "id": 223,
                    "user_id" : 124,
                    "description" : "transactions",
                    "value" : 100,
                    "balance" : 100,
                    "transaction_type" : "programme points",
                    "remote_transaction_id": 999,
                    "created_at" : "2016-03-18T02:20:02.000+00:00"
                }
            ]
       '''

        when:
        List<MyRewardsTransaction> entity = mapper.readValue(payload, new TypeReference<List<MyRewardsTransaction>>() {
        })

        then:
        entity.size() == 2
        entity[0].id == 123
        entity[1].id == 223
    }
}
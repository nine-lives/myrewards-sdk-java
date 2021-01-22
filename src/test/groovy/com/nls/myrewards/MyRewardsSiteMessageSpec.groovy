package com.nls.myrewards


import com.fasterxml.jackson.databind.ObjectMapper
import com.nls.myrewards.client.ObjectMapperFactory
import org.joda.time.DateTime
import spock.lang.Specification

class MyRewardsSiteMessageSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
            {
              "id": 2,
              "user_id": 68,
              "content": "Hello Fred. You have <strong>won a prize</strong>. Please visit the points area for more details.",
              "created_at": "2016-03-18T02:20:02.000+00:00"
            }
       '''

        when:
        MyRewardsSiteMessage entity = mapper.readValue(payload, MyRewardsSiteMessage.class)

        then:
        entity.id == 2
        entity.userId == 68
        entity.content == 'Hello Fred. You have <strong>won a prize</strong>. Please visit the points area for more details.'
        entity.createdAt == DateTime.parse("2016-03-18T02:20:02.000+00:00")
    }

    def "I can covert a list JSON payload to the entity"() {
        given:
        String payload = '''
            [
              {
                "id": 1,
                "user_id": 68,
                "content": "Hello Fred. You have <strong>won a prize</strong>. Please visit the points area for more details.",
                "created_at": "2016-03-18T02:20:02.000+00:00"
              },
              {
                "id": 2,
                "user_id": 69,
                "content": "Your Order is on it's way",
                "created_at": "2017-08-16T07:58:03.000+01:00"
              }
            ]
       '''

        when:
        List<MyRewardsSiteMessage> entity = mapper.readValue(payload, MyRewardsSiteMessage.LIST_TYPE_REFERENCE)

        then:
        entity.size() == 2
        entity[0].id == 1
        entity[1].id == 2
    }
}
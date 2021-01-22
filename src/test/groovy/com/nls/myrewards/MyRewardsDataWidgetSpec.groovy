package com.nls.myrewards


import com.fasterxml.jackson.databind.ObjectMapper
import com.nls.myrewards.client.ObjectMapperFactory
import spock.lang.Specification

class MyRewardsDataWidgetSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
            {
                "id": 456,
                "name": "Data widget name",
                "user_data_widget": {
                    "user_id": 123,
                    "data": "this is some text"
                }
            }
       '''

        when:
        MyRewardsDataWidget entity = mapper.readValue(payload, MyRewardsDataWidget)

        then:
        entity.id == 456
        entity.name == 'Data widget name'
        entity.userDataWidget.userId == 123
        entity.userDataWidget.data == 'this is some text'
    }

    def "I can covert a list JSON payload to the entity"() {
        given:
        String payload = '''
            [
              {
                "id": 1234,
                "name": "text"
              },
              {
                "id": 4567,
                "name": "some other text"
              }
            ]
       '''

        when:
        List<MyRewardsDataWidget> entity = mapper.readValue(payload, MyRewardsDataWidget.LIST_TYPE_REFERENCE)

        then:
        entity.size() == 2
        entity[0].id == 1234
        entity[0].name == 'text'
        entity[0].userDataWidget == null
        entity[1].id == 4567
        entity[1].name == 'some other text'
        entity[1].userDataWidget == null
    }
}
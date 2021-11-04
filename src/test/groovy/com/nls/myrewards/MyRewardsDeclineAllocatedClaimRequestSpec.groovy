package com.nls.myrewards

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.nls.myrewards.client.ObjectMapperFactory
import org.joda.time.LocalDate
import spock.lang.Specification

class MyRewardsDeclineAllocatedClaimRequestSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can covert a request to a payload"() {
        given:
        MyRewardsDeclineAllocatedClaimRequest request = new MyRewardsDeclineAllocatedClaimRequest()
            .withReasonForDeclineId("An id for a decline reason")
            .withReasonForDeclineText("Some reason text string")

        when:
        String json = mapper.writeValueAsString(request);
        Map<String, Object> entity = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});

        then:
        request.reasonForDeclineId == 'An id for a decline reason'
        request.reasonForDeclineText == 'Some reason text string'

        entity.reason_for_decline_id == 'An id for a decline reason'
        entity.reason_for_decline_text == 'Some reason text string'

        when:
        MyRewardsDeclineAllocatedClaimRequest result = mapper.readValue(json, MyRewardsDeclineAllocatedClaimRequest)

        then:
        result.reasonForDeclineId == 'An id for a decline reason'
        result.reasonForDeclineText == 'Some reason text string'
    }
}

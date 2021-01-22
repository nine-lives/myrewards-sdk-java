package com.nls.myrewards

import com.fasterxml.jackson.databind.ObjectMapper
import com.nls.myrewards.client.ObjectMapperFactory
import com.nls.myrewards.client.RequestParameterMapper
import spock.lang.Specification

class MyRewardsUserGroupRequestSpec extends Specification {
    private RequestParameterMapper mapper = new RequestParameterMapper()

    def "I can convert a request to a payload"() {
        given:
        MyRewardsUserGroupRequest request = new MyRewardsUserGroupRequest()
                .withName("Example User Group")
                .withPosition(1)
                .withParentId(3)
                .withImageUrl("http://example_hosted_image_url.com/image.png")

        when:
        Map<String, String> entity = mapper.writeToMap(request)

        then:
        request.name == 'Example User Group'
        entity.name == 'Example User Group'
        request.position == 1
        entity.position == '1'
        request.parentId == 3
        entity.parent_id == '3'
        request.imageUrl == 'http://example_hosted_image_url.com/image.png'
        entity.image_url == 'http://example_hosted_image_url.com/image.png'

        when:
        ObjectMapper om = ObjectMapperFactory.make()
        MyRewardsUserGroupRequest result = om.readValue(om.writeValueAsString(entity), MyRewardsUserGroupRequest)

        then:
        result.name == request.name
        result.position == 1
        result.parentId == 3
        result.imageUrl == 'http://example_hosted_image_url.com/image.png'
    }
}
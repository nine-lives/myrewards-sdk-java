package com.nls.myrewards.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.nls.myrewards.MyRewardsUserGroup
import com.nls.myrewards.client.ObjectMapperFactory
import spock.lang.Specification

class MyRewardsUserGroupTreeSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can build and navigate a tree of user groups"() {
        when:
        MyRewardsUserGroupTree tree = new MyRewardsUserGroupTree(groups);

        then:
        tree.children*.id == [1, 5]

        tree.find(1).id == 1
        tree.find(1).children*.id == [2]
        tree.find(2).children*.id == [3, 4]
        tree.find(2).find(3).id == 3
        tree.find(2).find(4).id == 4
        tree.find(3).id == 3
        tree.find(4).id == 4

        tree.find(5).children*.id == [6]
        tree.find(6).children*.id == [7, 8]

        tree.find(6).find(7).id == 7
        tree.find(6).find(8).id == 8
        tree.find(7).id == 7
        tree.find(8).id == 8

        when:
        List<MyRewardsUserGroupNode> all = tree.list()

        then:
        all.size() == 8
        all*.id as Set == [1,2,3,4,5,6,7,8] as Set

        when:
        List<MyRewardsUserGroupNode> marvel = tree.find(5).list()

        then:
        marvel.size() == 4
        marvel*.id as Set == [5,6,7,8] as Set


        when:
        MyRewardsUserGroupNode cyclops = tree.find(8)

        then:
        cyclops.id == 8
        cyclops.name == 'cyclops'
        cyclops.children.size() == 0
        cyclops.parentId == 6
        cyclops.imageUrl == 'one_eye.gif'
        cyclops.position == 2
        cyclops.programme == null
    }

    private List<MyRewardsUserGroup> getGroups() {
        String payload = '''
            [
              {
                "id" : 1,
                "name" : "dc comics",
                "parent_id" :  null,
                "default" : "false",
                "position" : 1
              },
              {
                "id" : 2,
                "name" : "justice league",
                "parent_id" : 1,
                "default" : "true",
                "position" : 1
              },
              {
                "id" : 3,
                "name" : "superman",
                "parent_id" : 2,
                "default" : "false",
                "position" : 1
              },
              {
                "id" : 4,
                "name" : "batman",
                "parent_id" : 2,
                "default" : "false",
                "position" : 2
              },
                            {
                "id" : 5,
                "name" : "marvel",
                "parent_id" :  null,
                "default" : "false",
                "position" : 2
              },
              {
                "id" : 6,
                "name" : "avengers",
                "parent_id" : 5,
                "default" : "true",
                "position" : 1
              },
              {
                "id" : 7,
                "name" : "wolverine",
                "parent_id" : 6,
                "default" : "false",
                "position" : 1
              },
              {
                "id" : 8,
                "name" : "cyclops",
                "parent_id" : 6,
                "default" : "false",
                "position" : 2,
                "image_url" : "one_eye.gif"
              }
            ]
       '''

        mapper.readValue(payload, MyRewardsUserGroup.LIST_TYPE_REFERENCE)
    }

}

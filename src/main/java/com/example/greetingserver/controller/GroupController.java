package com.example.greetingserver.controller;

import com.example.greetingserver.pojo.Group;
import com.example.greetingserver.pojo.GroupMember;
import com.example.greetingserver.pojo.GroupMemberQuery;
import com.example.greetingserver.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@EnableAutoConfiguration
@RestController
@RequestMapping("/group")
public class GroupController {
    @Autowired
    GroupService groupService;

    @RequestMapping("/getGroupMember")
    public Map<String, Object> call(@RequestBody Group group) {
        Map<String, Object> map = new HashMap<>();
        List<GroupMember> userInGroup = groupService.findUserInGroup(group.getName());
        Iterator<GroupMember> iterator = userInGroup.iterator();
        String nameList = "";
        while (iterator.hasNext()){
            nameList+= iterator.next().getUsername()+"/";
        }
        map.put("nameList", nameList);
        return map;
    }

}

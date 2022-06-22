package com.example.greetingserver.controller;

import com.example.greetingserver.pojo.Group;
import com.example.greetingserver.pojo.GroupMember;
import com.example.greetingserver.pojo.GroupMemberQuery;
import com.example.greetingserver.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

    @PostMapping(path = "/doAddGroup")
    public Map<String, Object> createGroup(@RequestBody Group group, HttpServletRequest servletRequest) throws IOException {
        Map<String, Object> map = new HashMap<>();
        String username = servletRequest.getUserPrincipal().getName();
        List<Integer> createGroupFlag = groupService.createGroup(group.getName(),1,username);
        if(createGroupFlag.get(0) > 0){
            map.put("createGroupFlag", "success");
        }else {
            map.put("createGroupFlag", "error");
        }
        return map;
    }

}

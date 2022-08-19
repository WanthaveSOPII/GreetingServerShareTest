package com.example.greetingserver.controller;

import com.example.greetingserver.pojo.Group;
import com.example.greetingserver.pojo.GroupMember;
import com.example.greetingserver.pojo.GroupMemberQuery;
import com.example.greetingserver.service.GroupService;
import com.google.gson.Gson;
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
    public List<GroupMember> getGroupMember(@RequestBody Group group) {
        Map<String, Object> map = new HashMap<>();
        List<GroupMember> userInGroup = groupService.findUserInGroup(group.getName());
        //String str = new Gson().toJson(userInGroup);
        //return str;
        return userInGroup;
    }

//    @RequestMapping("/getGroupMember")
//    public Map<String, Object> getGroupMember(@RequestBody Group group) {
//        Map<String, Object> map = new HashMap<>();
//        List<GroupMember> userInGroup = groupService.findUserInGroup(group.getName());
//        Iterator<GroupMember> iterator = userInGroup.iterator();
//        String nameList = "";
//        while (iterator.hasNext()){
//            nameList+= iterator.next().getUsername()+"/";
//        }
//        map.put("nameList", nameList);
//        return map;
//    }
//
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

    @PostMapping(path = "/doOutFromGroup")
    public Map<String, Object> outFromGroup(@RequestBody Group group, HttpServletRequest servletRequest) throws IOException {
        Map<String, Object> map = new HashMap<>();
        String username = servletRequest.getUserPrincipal().getName();
        List<Integer> leftGroupFlag = groupService.leftGroup(username,group.getName());
        if(leftGroupFlag.get(0) == 1){
            map.put("leftGroupFlag", "success");
        }else {
            map.put("leftGroupFlag", "error");
        }
        return map;
    }

    @PostMapping(path = "/doDeleteGroup")
    public Map<String, Object> deleteGroup(@RequestBody Group group, HttpServletRequest servletRequest) throws IOException {
        Map<String, Object> map = new HashMap<>();
        List<Integer> deleteGroupFlag = groupService.deleteGroup(group.getName());
        if(deleteGroupFlag.get(0) == 1){
            map.put("outFromGroupFlag", "success");
        }else {
            map.put("outFromGroupFlag", "error");
        }
        return map;
    }

    @PostMapping(path = "/doInviteToGroup")
    public Map<String, Object> inviteToGroup(@RequestBody GroupMember groupMember, HttpServletRequest servletRequest) throws IOException {
        Map<String, Object> map = new HashMap<>();
        String username = servletRequest.getUserPrincipal().getName();
        if (groupService.findGroupOwner(groupMember.getGroupname()).equals(username)){
            Integer inviteToGroupFlag = groupService.joinGroup(groupMember.getUsername(),groupMember.getGroupname());
            if((inviteToGroupFlag == null)||(inviteToGroupFlag == -1)){
                map.put("inviteToGroupFlag", "error");
            }else if(inviteToGroupFlag == 1){
                map.put("inviteToGroupFlag", "success");
            }else if(inviteToGroupFlag == 0){
                map.put("inviteToGroupFlag", "ingroup");
            }
        }else {
            map.put("inviteToGroupFlag", "No permission");
        }
        return map;
    }

    @PostMapping(path = "/doJoinGroup")
    public Map<String, Object> joinGroup(@RequestBody Group group, HttpServletRequest servletRequest) throws IOException {
        Map<String, Object> map = new HashMap<>();
        String username = servletRequest.getUserPrincipal().getName();
        Integer joinGroupFlag = groupService.joinGroup(username,group.getName());
        if((joinGroupFlag == null)||(joinGroupFlag == -1)){
            map.put("joinGroupFlag", "error");
        }else if(joinGroupFlag == 1){
            map.put("joinGroupFlag", "success");
        }else if(joinGroupFlag == 0){
            map.put("joinGroupFlag", "ingroup");
        }
        return map;
    }

}

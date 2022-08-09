package com.example.greetingserver.service;

import com.example.greetingserver.mapper.GroupMapper;
import com.example.greetingserver.pojo.Group;
import com.example.greetingserver.pojo.GroupMember;
import com.example.greetingserver.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {
    @Autowired
    GroupMapper groupMapper;
    public List<Group> findAll(){
        List<Group> groups = groupMapper.findAll();

//        for (User ausr: users) {
//            ausr.processIcon();
//        }
        return groups;
    };

    public List<Group> findUserGroups(String username){
        List<Group> groups = groupMapper.findUserGroups(username);

//        for (User ausr: users) {
//            ausr.processIcon();
//        }
        return groups;
    };

    public List<GroupMember> findUserInGroup(String groupname){
        List<GroupMember> userInGroup = groupMapper.findUserInGroup(groupname);
        return userInGroup;
    }

    public List<Integer> createGroup(String groupname,Integer type,String ownername){
//        List<Integer> createGroupFlag = groupMapper.createGroup(groupname,type,ownername);
        Group group = new Group();
        group.setName(groupname);
        group.setType("public");
        group.setOwnername(ownername);
        List<Integer> createGroupFlag = groupMapper.createGroup(group);
        return createGroupFlag;
    }

    public List<Integer> leftGroup(String username,String groupname){
//        List<Integer> createGroupFlag = groupMapper.createGroup(groupname,type,ownername);
        GroupMember groupMember = new GroupMember();
        groupMember.setUsername(username);
        groupMember.setGroupname(groupname);
        List<Integer> outFromGroupFlag = groupMapper.leftGroup(groupMember);
        return outFromGroupFlag;
    }

    public List<Integer> deleteGroup(String groupname){
//        List<Integer> createGroupFlag = groupMapper.createGroup(groupname,type,ownername);
        Group group = new Group();
        group.setName(groupname);
        List<Integer> deleteGroupFlag = groupMapper.deleteGroup(group);
        return deleteGroupFlag;
    }

    public Integer joinGroup(String username,String groupname){
//        List<Integer> createGroupFlag = groupMapper.createGroup(groupname,type,ownername);
        GroupMember groupMember = new GroupMember();
        groupMember.setGroupname(groupname);
        groupMember.setUsername(username);
        Integer joinGroupFlag;
        List<Integer> joinGroupRes = groupMapper.joinGroup(groupMember);
        if((joinGroupRes!=null )&& (joinGroupRes.size()>0)) {
            joinGroupFlag = joinGroupRes.get(0);
        }else {
            joinGroupFlag = null;
        }
        return joinGroupFlag;
    }

    public String findGroupOwner(String groupname){
        Group group = new Group();
        group.setName(groupname);
        List<User> findGroupOwner = groupMapper.findGroupOwner(group);
        return findGroupOwner.get(0).getUsername();
    }

}

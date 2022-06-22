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
}

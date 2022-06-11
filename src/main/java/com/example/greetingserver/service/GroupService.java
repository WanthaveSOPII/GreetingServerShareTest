package com.example.greetingserver.service;

import com.example.greetingserver.mapper.GroupMapper;
import com.example.greetingserver.pojo.Group;
import com.example.greetingserver.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

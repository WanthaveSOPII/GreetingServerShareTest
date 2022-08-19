package com.example.greetingserver.controller;

import com.example.greetingserver.pojo.Group;
import com.example.greetingserver.pojo.GroupMember;
import com.example.greetingserver.pojo.Message;
import com.example.greetingserver.service.MessageService;
import com.example.greetingserver.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableAutoConfiguration
@RestController
public class MessageController {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @RequestMapping("/message/getPicMsg")
    public Message getMessages(@RequestBody Message msg) {
        msg = messageService.findById(msg.getId()).get(0);

        return msg;
    }

    @RequestMapping("/message/findTopTenInGroup")
    public List<Message> findTopTenInGroup(@RequestBody Group group) {
        List<Message> topTenMsg = messageService.findTopTenInGroup(group.getName());

        return topTenMsg;
    }
//    public String getGroupMember(@RequestBody Group group) {
//        Map<String, Object> map = new HashMap<>();
//        List<GroupMember> userInGroup = groupService.findUserInGroup(group.getName());
//        String str = new Gson().toJson(userInGroup);
//        return str;
//    }
}
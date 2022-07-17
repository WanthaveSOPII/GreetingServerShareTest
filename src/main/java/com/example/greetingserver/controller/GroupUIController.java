package com.example.greetingserver.controller;

import com.example.greetingserver.pojo.Group;
import com.example.greetingserver.pojo.GroupMember;
import com.example.greetingserver.pojo.User;
import com.example.greetingserver.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@EnableAutoConfiguration
@Controller
@RequestMapping("/group")
public class GroupUIController {
    @Autowired
    GroupService groupService;

    @RequestMapping("/list" )
    public String listGroups(Model model) {
        //List<User> users = userMapper.findAll();
        List<Group> groups = groupService.findAll();
        model.addAttribute("groups", groups);
        return "listGroup";
    }

}

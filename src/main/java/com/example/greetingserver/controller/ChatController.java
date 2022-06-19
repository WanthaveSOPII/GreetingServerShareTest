package com.example.greetingserver.controller;

import com.example.greetingserver.pojo.Group;
import com.example.greetingserver.pojo.Message;
import com.example.greetingserver.pojo.User;
import com.example.greetingserver.service.GroupService;
import com.example.greetingserver.service.MessageService;
import com.example.greetingserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@EnableAutoConfiguration
@Controller
//@ServerEndpoint(value = "/chatPage/zhangsan", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class ChatController {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    GroupService groupService;

    @RequestMapping("/chatPage")
    public String chatPage(Model model, HttpServletRequest req) {
        String me = "zhangsan";

        //子颉： LOOK!
        me = req.getUserPrincipal().getName();

        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        List<Group> groups = groupService.findAll();
        model.addAttribute("groups", groups);
        List<Message> messages = messageService.findTopTen();
        model.addAttribute("messages", messages);
        model.addAttribute("me", me);
        return "chatPage";
    }

}
package com.example.greetingserver.controller;

import com.example.greetingserver.pojo.Message;
import com.example.greetingserver.pojo.User;
import com.example.greetingserver.service.MessageService;
import com.example.greetingserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@EnableAutoConfiguration
@Controller
public class ChatController {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @RequestMapping("/chatPage")
    public String chatPage(Model model, HttpServletRequest req) {
        String me = "zhangsan";

        //子颉： LOOK!
        me = req.getUserPrincipal().getName();

        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        List<Message> messages = messageService.findAll();
        model.addAttribute("messages", messages);
        model.addAttribute("me", me);
        return "chatPage";
    }
}
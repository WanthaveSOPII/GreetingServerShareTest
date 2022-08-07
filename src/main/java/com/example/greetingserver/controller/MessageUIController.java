package com.example.greetingserver.controller;

import com.example.greetingserver.mapper.MessageMapper;
import com.example.greetingserver.pojo.Message;
import com.example.greetingserver.pojo.User;
import com.example.greetingserver.service.MessageService;
import com.example.greetingserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@EnableAutoConfiguration
@Controller
public class MessageUIController {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @RequestMapping("/listMessage")
    public String listMessage(Model model, HttpServletRequest request) {
        List<Message> messages = messageService.findAll();
        model.addAttribute("messages", messages);
        model.addAttribute("NickName",request.getUserPrincipal().getName());
        return "showMessages";
    }
}
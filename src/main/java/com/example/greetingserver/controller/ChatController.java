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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

//    @PostMapping(path = "/group/doAddGroup")
//    public String createGroup(@RequestParam("groupname") String groupname, HttpServletRequest servletRequest) throws IOException {
//        //Map<String, Object> map = new HashMap<>();
//        String username = servletRequest.getUserPrincipal().getName();
//        List<Integer> createGroupFlag = groupService.createGroup(groupname,1,username);
////        if(createGroupFlag.get(0) > 0){
////            map.put("createGroupFlag", "success");
////        }else {
////            map.put("createGroupFlag", "error");
////        }
//        return "redirect:/chatPage";
//    }
//

}
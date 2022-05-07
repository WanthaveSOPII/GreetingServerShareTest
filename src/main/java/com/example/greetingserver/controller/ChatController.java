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

//    private Session session;
//    private static final Set<ChatController> chatEndpoints = new CopyOnWriteArraySet<>();
//    private static HashMap<String, String> users = new HashMap<>();
//
//    @OnOpen
//    public void onOpen(Session session, HttpServletRequest req) throws IOException, EncodeException {
//        String username = req.getUserPrincipal().getName();
//        this.session = session;
//        chatEndpoints.add(this);
//        users.put(session.getId(), username);
//    }
//
//    @OnMessage
//    public void onMessage(Session session, Message message) throws IOException, EncodeException {
////        message.setFrom(users.get(session.getId()));
////        broadcast(message);
//    }
//
//    @OnClose
//    public void onClose(Session session) throws IOException, EncodeException {
//        chatEndpoints.remove(this);
////        Message message = new Message();
////        message.setFrom(users.get(session.getId()));
////        message.setContent("Disconnected!");
////        broadcast(message);
//    }
//
//    @OnError
//    public void onError(Session session, Throwable throwable) {
//        // Do error handling here
//    }
//
////    private static void broadcast(Message message) throws IOException, EncodeException {
////        chatEndpoints.forEach(endpoint -> {
////            synchronized (endpoint) {
////                try {
////                    endpoint.session.getBasicRemote()
////                            .sendObject(message);
////                } catch (IOException | EncodeException e) {
////                    e.printStackTrace();
////                }
////            }
////        });
////    }
}
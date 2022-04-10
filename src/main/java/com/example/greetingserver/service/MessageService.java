package com.example.greetingserver.service;

import com.example.greetingserver.mapper.MessageMapper;
import com.example.greetingserver.mapper.UserMapper;
import com.example.greetingserver.pojo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MessageService {
    @Autowired
    MessageMapper messageMapper;

    public List<Message> findAll(){
        List<Message> messageList;
        messageList = messageMapper.findAll();
        for (Message msg:messageList) {
            String msgHead = "Receiver:";
            msg.setRecver(msgHead+msg.getRecver());
        }
        return messageList;
    };
}
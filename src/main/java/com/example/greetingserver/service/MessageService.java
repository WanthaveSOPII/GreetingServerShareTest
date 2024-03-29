package com.example.greetingserver.service;

import com.example.greetingserver.mapper.MessageMapper;
import com.example.greetingserver.mapper.UserMapper;
import com.example.greetingserver.pojo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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

    //找出在组里的十条信息
    public List<Message> findTopTenInGroup(String groupName){
        List<Message> messageList;
        List<Message> messageReverse = new ArrayList<Message>();
        messageList = messageMapper.findTopTenInGroup(groupName);

        for(int i = messageList.size()-1;i>=0;i--){
            messageReverse.add(messageList.get(i));
        }
        for (Message msg:messageReverse) {
            String msgHead = "Receiver:";
            msg.setRecver(msgHead+msg.getRecver());
            msg.processPicture();
        }
        return messageReverse;
    };

    //找出在大厅的十条信息
    public List<Message> findTopTen(){
        List<Message> messageList;
        List<Message> messageReverse = new ArrayList<Message>();
        messageList = messageMapper.findTopTen();

        for(int i = messageList.size()-1;i>=0;i--){
            messageReverse.add(messageList.get(i));
        }
        for (Message msg:messageReverse) {
            String msgHead = "Receiver:";
            msg.setRecver(msgHead+msg.getRecver());
            msg.processPicture();
        }
        return messageReverse;
    };

    public List<Message> findById(int msgID){
        List<Message> messageList = messageMapper.findByID(msgID);
        for (Message msg:messageList) {
            String msgHead = "Receiver:";
            msg.setRecver(msgHead+msg.getRecver());
        }
        return messageList;
    };

    public void insertMessage(Message msg){
        messageMapper.insertMessage(msg);
    }

    public int insertPicMsg(Message msg){
        List<Integer> msgID = messageMapper.insertPicMsg(msg);
        return msgID.get(0);
    }
}
package com.example.greetingserver.controller;

import com.example.greetingserver.pojo.Message;
import com.example.greetingserver.pojo.User;
import com.example.greetingserver.service.GroupService;
import com.example.greetingserver.service.MessageService;
import com.example.greetingserver.service.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint(value = "/wsPage/{username}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class WsController {

//    @Autowired
    /**
     SeverEndPoint与Autowired冲突导致，只能用方法的Autowired
     */
    private static MessageService messageService;
    @Autowired
    public void setMessageService(MessageService messageService){WsController.messageService=messageService;}

    @Autowired
    UserService userService;
    GroupService groupService;

    private Session session;
    private static final Set<WsController> chatEndpoints = new CopyOnWriteArraySet<>();
    private static HashMap<String, String> users = new HashMap<>();
    private String inGroup;
    private static HashMap<String, FileUploadStatus> largeFile = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException, EncodeException {
        this.session = session;
        chatEndpoints.add(this);
        users.put(session.getId(), username);
        this.sendUserList(username);

    }

    public void sendUserList(String username) throws IOException, EncodeException{
        Message message = new Message();
        message.setType(Message.MSGTYPE_LISTUSER);
        message.setSender("SYSTEM");
        message.setRecver(username);
        message.setInfo(allUsersList());
        this.unicast(message);
    }

    public void broadcastBye(String username) throws IOException, EncodeException{
        Message message = new Message();
        message.setType(Message.MSGTYPE_BYE);
        message.setSender("SYSTEM");
        message.setRecver("ALLUSER");
        message.setInfo(username);
        this.unicast(message);
    }

    private String allUsersList(){
        String username = users.get(this.session.getId());

        List<User> userList = new ArrayList<User>();
        User member = new User();
        member.setUsername(username);
        userList.add(member);
        Iterator<Map.Entry<String, String>> iterator = this.users.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            if(next.getValue().compareTo(username)!=0) {
                member = new User();
                member.setUsername(next.getValue());
                userList.add(member);
            }
        }
        String res = new Gson().toJson(userList);

        return res;
    }
    @OnMessage
    public void onMessage(Session session, Message message) throws IOException, EncodeException {
        if(message.getType().equals(Message.MSGTYPE_HELLO)){
            broadcast(message);
        }else if(message.getType().equals(Message.MSGTYPE_CHAT)){
            message.setSender(users.get(session.getId()));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date datetime = null;
            try {
                datetime = df.parse(message.getStringTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Timestamp t = new Timestamp(datetime.getTime());
            message.setTime(t);
            messageService.insertMessage(message);
            broadcast(message);
        }else if(message.getType().equals(Message.MSGTYPE_LEFTGROUP)){
            message.setRecver(message.getSender());
            message.setSender("SYSTEM");
            selfcast(message);
        }else if(message.getType().equals(Message.MSGTYPE_PICTURE)){
            message.setSender(users.get(session.getId()));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date datetime = null;
            try {
                datetime = df.parse(message.getStringTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Timestamp t = new Timestamp(datetime.getTime());
            message.setTime(t);
            broadcast(message);
        }else if(message.getType().equals(Message.MSGTYPE_UPLOADLARGEFILE)){
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(message.getInfo());
            JsonElement jsonElement;
            InfoMap map = new InfoMap();
            FileUploadStatus fileUploadStatus;
            String status = null;
            //检查info的前台状态
            if((jsonElement = jsonObject.get("status"))!=null) {
                status = jsonElement.getAsString();
            }
            //如果状态为空返回错误
            switch(status){
                case "init":
                //1.以下为状态为init的情况下的处理
                    if(largeFile.get(session.getId())!=null){
                        return;
                    }
                //允许新请求，把文件信息存下
                    fileUploadStatus = new FileUploadStatus();

                    if((jsonElement = jsonObject.get("fileSize"))!=null) {
                        fileUploadStatus.fileSize = jsonElement.getAsInt();
                    }
                    if((jsonElement = jsonObject.get("shardSize"))!=null) {
                        fileUploadStatus.shardSize = jsonElement.getAsInt();

                    }
                    if((jsonElement = jsonObject.get("fileType"))!=null) {
                        fileUploadStatus.fileType = jsonElement.getAsString();
                    }
                    if((jsonElement = jsonObject.get("fileName"))!=null) {
                        fileUploadStatus.fileName = jsonElement.getAsString();
                    }
                    if((jsonElement = jsonObject.get("shardCount"))!=null) {
                        fileUploadStatus.shardCount = jsonElement.getAsInt();
                        for(int i = 0;i < fileUploadStatus.shardCount;i++){
                            fileUploadStatus.data.add("");
                        }
                    }
                    if((jsonElement = jsonObject.get("id"))!=null) {
                        fileUploadStatus.id = jsonElement.getAsString();
                    }

                    fileUploadStatus.status = status;


                    largeFile.put(session.getId(),fileUploadStatus);
                    map = new InfoMap();
                    map.id = fileUploadStatus.id;
                    map.status = "initCompleted";
                    message.setInfo(new Gson().toJson(map));
                    message.setRecver(message.getSender());
                    message.setSender("SYSTEM");
                    unicast(message);
                //发回请求被接受的信息
                    break;
                case "started":
                //2.以下为started的处理
                    fileUploadStatus = largeFile.get(session.getId());
                    fileUploadStatus.status = status;
                    break;
                case "uploading":
                //3.以下为uploading的处理
                    String base64 ="base64,";
                    int base64Index = -1;
                    fileUploadStatus = largeFile.get(session.getId());
                    String slicedData = "";
                    int shardIndex = -1;
                    if((jsonElement = jsonObject.get("slicedFile"))!=null) {
                        slicedData = jsonElement.getAsString();
                        base64Index = slicedData.indexOf(base64);
                        if (base64Index >= 0){
                            slicedData = slicedData.substring(base64Index + base64.length());
                        }
                    }
                    if((jsonElement = jsonObject.get("shardIndex"))!=null) {
                        shardIndex = jsonElement.getAsInt();
                    }
                    if(shardIndex!=-1) {
                        fileUploadStatus.data.set(shardIndex, slicedData);
                    }
                    largeFile.replace(session.getId(),fileUploadStatus);
                    map = new InfoMap();
                    map.id = fileUploadStatus.id;
                    map.shardIndex = shardIndex;
                    map.status = "shardCompleted";
                    message.setInfo(new Gson().toJson(map));
                    message.setRecver(message.getSender());
                    message.setSender("SYSTEM");
                    unicast(message);
                    break;

                case "completed":
                    fileUploadStatus = largeFile.get(session.getId());
                    for(int i=0;i<fileUploadStatus.shardCount;i++){
                        fileUploadStatus.file += fileUploadStatus.data.get(i);
                    }
                    Message sqlMsg = new Message();
                    sqlMsg.setSender(users.get(session.getId()));

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    java.util.Date datetime = null;
                    try {
                        datetime = df.parse(message.getStringTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Timestamp t = new Timestamp(datetime.getTime());
                    sqlMsg.setTime(t);

                    sqlMsg.setRecver("ALLUSER");
                    sqlMsg.setZoneID(message.getZoneID());
                    sqlMsg.setPicture(DatatypeConverter.parseBase64Binary(fileUploadStatus.file));
                    int msgID = messageService.insertPicMsg(sqlMsg);
                    message.setId(msgID);
                    map = new InfoMap();
                    map.id = fileUploadStatus.id;
                    map.status = "fileUploadCompleted";
                    message.setInfo(new Gson().toJson(map));
                    message.setSender("SYSTEM");
                    message.setRecver("ALLUSER");
                    broadcast(message);
                    largeFile.remove(session.getId());
                    //把图片信息当做是一个新的message存入数据库
                    //获取这个message的id
                    //告诉所有用户去取这条信息
                //4.以下为completed的处理
                    break;
            };

        }
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        chatEndpoints.remove(this);
        String username = users.get(this.session.getId());
        users.remove(this.session.getId());
        broadcastBye(username);
//        Message message = new Message();
//        message.setFrom(users.get(session.getId()));
//        message.setContent("Disconnected!");
//        broadcast(message);
    }


    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }

    private static void broadcast(Message message) throws IOException, EncodeException {
        chatEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote()
                            .sendObject(message);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void unicast(Message message) throws IOException, EncodeException {
                try {
                    this.session.getBasicRemote()
                            .sendObject(message);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }

    private void selfcast(Message message) throws IOException, EncodeException {
        ArrayList<String> userSessions = new ArrayList<>();
        String username = users.get(this.session.getId());
        for(Map.Entry<String, String> session :  users.entrySet()){
            if(session.getValue().equals(username)){
                userSessions.add(session.getKey());
            }
        }
        chatEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    if(userSessions.contains(endpoint.session.getId())) {
                        endpoint.session.getBasicRemote()
                                .sendObject(message);
                    }
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class FileUploadStatus{
        String id;
        String fileName;
        ArrayList<String> data = new ArrayList<String>();
        String file = "";
        int shardSize;
        int fileSize;
        String fileType;
        int shardCount;
        String status;
    }
    class InfoMap{
        String id;
        int shardIndex = -1;
        String status;
    }
}


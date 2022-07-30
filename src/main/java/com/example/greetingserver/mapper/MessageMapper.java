package com.example.greetingserver.mapper;

import com.example.greetingserver.pojo.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Mapper

//@Repository
public interface MessageMapper {

    @Select("SELECT * FROM message m LEFT JOIN message_picture mp ON m.id = mp.messageID")
    List<Message> findAll();

    @Select("SELECT * FROM message m LEFT JOIN message_picture mp ON m.id = mp.messageID Order by time DESC limit 10")
    List<Message> findTopTen();

    @Insert("insert into message(info,sender,time,recver,zoneID) values(#{info},#{sender},#{time},#{recver},#{zoneID})")
    void insertMessage(Message msg);
//    public boolean insertMessage(Message msg) throws ParseException {
//
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        java.util.Date datetime = df.parse(msg.time);
//        Timestamp t = new Timestamp(datetime.getTime());
//
//    }
}
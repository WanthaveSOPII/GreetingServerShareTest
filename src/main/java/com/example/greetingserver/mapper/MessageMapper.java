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

    @Select("SELECT * FROM message m LEFT JOIN message_picture mp ON m.id = mp.messageID WHERE m.recver='ALLUSERS' Order by time DESC limit 10 ")
    List<Message> findTopTen();

    @Select("SELECT * FROM message m LEFT JOIN message_picture mp ON m.id = mp.messageID WHERE m.recver= #{groupName} Order by time DESC limit 10 ")
    List<Message> findTopTenInGroup(String groupName);

    @Select("SELECT * FROM message m LEFT JOIN message_picture mp ON m.id = mp.messageID WHERE m.id = #{msgID};")
    List<Message> findByID(Integer msgID);

    //目前group名字和recver是相同的
    @Insert("CALL insertMsg(#{info},#{sender},#{time},#{recver},#{zoneID},#{recver})")
    void insertMessage(Message msg);

    //目前group名字和recver是相同的
    @Select("CALL insertPicMsg(#{picture},#{sender},#{time},#{recver},#{zoneID},#{recver})")
    List<Integer> insertPicMsg(Message msg);
//    public boolean insertMessage(Message msg) throws ParseException {
//
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        java.util.Date datetime = df.parse(msg.time);
//        Timestamp t = new Timestamp(datetime.getTime());
//
//    }
}
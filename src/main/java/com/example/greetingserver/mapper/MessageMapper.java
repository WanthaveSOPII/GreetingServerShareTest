package com.example.greetingserver.mapper;

import com.example.greetingserver.pojo.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper

//@Repository
public interface MessageMapper {

    @Select("SELECT * FROM message")
    List<Message> findAll();
}
package com.example.greetingserver.mapper;

import com.example.greetingserver.pojo.Group;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface GroupMapper {
    @Select("SELECT Id, Name, CreateTime ,GroupOwnerid, Active ,Type, Status FROM `group` WHERE active=1 AND type=1")
    List<Group> findAll();
}

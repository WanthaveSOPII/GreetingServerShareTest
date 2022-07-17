package com.example.greetingserver.mapper;

import com.example.greetingserver.pojo.Group;
import com.example.greetingserver.pojo.GroupMember;
import com.example.greetingserver.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface GroupMapper {
    @Select("SELECT Id, Name, CreateTime ,GroupOwnerid, Active ,Type, Status FROM `group` WHERE active=1 AND type=1")
    List<Group> findAll();

    @Select("CALL findUserGroups(#{username})")
    List<Group> findUserGroups(String username);

    @Select("CALL findUserInGroup(#{groupname})")
    List<GroupMember> findUserInGroup(String groupname);

//    @Select("CALL createGroup(#{groupname},#{type},#{ownername})")
//    List<Integer> createGroup(String groupname, Integer type,String ownername);

    @Select("CALL createGroup(#{name},#{type},#{ownername})")
    List<Integer> createGroup(Group group);


    @Select("CALL outFromGroup(#{username},#{groupname},'LEFT')")
    List<Integer> leftGroup(GroupMember groupMember);

    @Select("CALL outFromGroup(#{username},#{groupname},'REMOVED')")
    List<Integer> removedFromGroup(GroupMember groupMember);

    @Select("CALL deleteGroup(#{name})")
    List<Integer> deleteGroup(Group group);

    @Select("CALL joinGroup(#{username},#{groupname})")
    List<Integer> joinGroup(GroupMember groupMember);

    @Select("SELECT username From `group` a INNER JOIN `user` b ON a.groupOwnerid = b.id WHERE a.`name` = #{name}")
    List<User> findGroupOwner(Group group);
}

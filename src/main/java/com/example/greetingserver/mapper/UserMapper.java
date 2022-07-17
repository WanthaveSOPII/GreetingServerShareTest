package com.example.greetingserver.mapper;

import com.example.greetingserver.pojo.User;
import com.example.greetingserver.pojo.UserRoles;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper

//@Repository
public interface UserMapper {
    //@Insert("INSERT INTO user(username,nickname,active) VALUES(#{username},#{nickname},1)")
    //@Insert("CALL addUser(#{username},#{nickname})")

    @Select("CALL insertUser(#{username},#{nickname},#{password})")
    List<Integer> addUser(User user);

    @Select("SELECT Id, UserName, Nickname, Active ,IsSystem,Icon FROM user WHERE active=1")
    List<User> findAll();

    @Select("SELECT Count(*) FROM user where UserName = #{username}")
    List<Integer> checkUniqueUsername(String username);

    @Select("SELECT Count(*) FROM user where Nickname = #{nickname}")
    List<Integer> checkUniqueNickname(String nickname);
    //public int addUser(Map<String, String> parm);

    @Insert("update user set Icon = #{icon} where id = #{id}")
    void insertIcon(User user);

    @Select("SELECT * FROM user where UserName = #{username}")
    List<User> getUserByName(String username);

    @Select("SELECT Id, UserName, Nickname, Active ,IsSystem,Icon FROM user where UserName = #{username}")
    List<User> getUserInfoByName(String username);

    @Select("CALL getUserRoles(#{username})")
    List<UserRoles> getUserRoles(String username);

//    @Select("CALL deleteUser(#{id})")
//    List<Integer> deleteUser(int id);

    @Select("SELECT deleteOneUser(#{username})")
    List<Integer> deleteUser(String username);



}

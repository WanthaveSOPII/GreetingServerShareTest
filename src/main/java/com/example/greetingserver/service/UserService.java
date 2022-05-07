package com.example.greetingserver.service;

import com.example.greetingserver.mapper.UserMapper;
import com.example.greetingserver.pojo.User;
import com.example.greetingserver.pojo.UserRoles;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
//    public int addUser(Map<String,String > parm){
//        return userMapper.addUser(parm);
//    }
    /**
     *
     * @param user 用户对象
     * @return int
     * >0的int:添加成功返回id
     * 0:添加失败
     * -1:操作出错
     */
    public int addUser(User user){
        List<Integer> res = null;

        if(user==null) {
            return -1;
        }

        String salt =BCrypt.gensalt();
        user.setPassword(BCrypt.hashpw(user.getPassword(),salt));
        res = userMapper.addUser(user);
        if((res!=null)&&(res.size()==1)){
            if(res.get(0)==0){
                return 0;
            }else if(res.get(0)>0) {
                return res.get(0);
            }
        }
        return -1;
    }

    /**
     *
     * @param username 用户名
     * @return int
     * 1:删除成功
     * 0:用户不存在
     * -1:删除出错
     */
    public int deleteUser(String username){
        List<Integer> res = null;

        res = userMapper.deleteUser(username);
        if((res!=null)&&(res.size()==1)){
            if(res.get(0)==1){
                return 1;
            }else if(res.get(0)==0) {
                return 0;
            }
        }
        return -1;
    }

    public int insertIcon(User user){
        userMapper.insertIcon(user);
        return 0;
    }

    //hasAuthority不需要前缀，hasROLE需要ROLE_前缀
//    @PreAuthorize("hasAuthority('stranger') or hasAuthority('admin')")
    public List<User> findAll(){
        List<User> users = userMapper.findAll();

        for (User ausr: users) {
            ausr.processIcon();
        }
        return users;
    };

    /**
     *
      * @param username 输入的用户名
     * @return int
     * 1:可以添加
     * 0:库中已有该名字不可添加
     * -1:操作出错
     */
    public int checkUniqueUsername(String username){
        List<Integer> res = userMapper.checkUniqueUsername(username);
        if((res!=null)&&(res.size()==1)){
            if(res.get(0)==1){
                return 0;
            }else if(res.get(0)==0) {
                return 1;
            }
        }
        return -1;
    }

    /**
     *
     * @param nickname 输入的昵称
     * @return int
     * 1:可以添加
     * 0:库中已有该昵称不可添加
     * -1:操作出错
     */
    public int checkUniqueNickname(String nickname){
        List<Integer> res = userMapper.checkUniqueNickname(nickname);
        if((res!=null)&&(res.size()==1)){
            if(res.get(0)==1){
                return 0;
            }else if(res.get(0)==0) {
                return 1;
            }
        }
        return -1;
    }

    public User getUserByName(String username){

        List<User> userInfo = userMapper.getUserByName(username);
        if((userInfo==null)||(userInfo.size()==0)){
            return null;
        }
        User user = userInfo.get(0);
        if(user == null){
            return null;
        }
        user.setRoles(getUserRoles(username));
        return user;
    }

    public User getUserInfoByName(String username){

        List<User> userInfo = userMapper.getUserInfoByName(username);
        if((userInfo==null)||(userInfo.size()==0)){
            return null;
        }
        User user = userInfo.get(0);
        if(user == null){
            return null;
        }
        user.processIcon();
        return user;
    }

    public List<UserRoles> getUserRoles(String username){
        List<UserRoles> userRoles = userMapper.getUserRoles(username);
        return userRoles;
    }


}
package com.example.greetingserver.service;

import com.example.greetingserver.controller.UserController;
import com.example.greetingserver.mapper.UserMapper;
import com.example.greetingserver.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.
        UserDetailsService;
import org.springframework.security.core.userdetails
        .UsernameNotFoundException;

import java.util.List;

public class CustomUserService implements UserDetailsService {

    @Autowired
    UserService userService;
    @Override
    public UserDetails loadUserByUsername(String s)
            throws UsernameNotFoundException {

        User user = userService.getUserByName(s);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        System.out.println("s:"+s);
        System.out.println("username:"+user.getUsername());
        return user;
    }
}

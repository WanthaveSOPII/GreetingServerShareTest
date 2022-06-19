package com.example.greetingserver.controller;

import com.example.greetingserver.dao.StudentProperties;
import com.example.greetingserver.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.util.Date;

@EnableAutoConfiguration
@Controller
public class HelloController {

    @Autowired
    private StudentProperties studentProperties;

    @RequestMapping("/hello")
    public String hello(Model m, HttpServletRequest request) {
        m.addAttribute("now", DateFormat.getDateInstance().format(new Date()));
        User userInfo = (User) request.getSession().getAttribute("UserInfo");
//        if(userInfo!=null){
//            m.addAttribute("NickName",userInfo.getNickname());
//        }
        {
            //子颉： 想想以下情况。 加了安全系统后，doLogin函数已经不被使用了，你没法存用户信息了
            //现在的nickname实际上是username，需要把nickname存入UserPrincipal()
            //看看UserPrincipal()还能取出什么信息
            userInfo = new User();
            userInfo.setUsername(request.getUserPrincipal().getName());
            userInfo.setNickname(userInfo.getUsername());
            request.getSession().setAttribute("UserInfo", userInfo);
            m.addAttribute("NickName",userInfo.getNickname());
            String adminonly = "这条只有管理员才能看得见";
            m.addAttribute("adminonly",adminonly);
        }
        return "hello";

        //return studentProperties.getName() + studentProperties.getAge();
    }

    @RequestMapping("/test")
    public String testUI(){
        System.out.println("test");
        return "test";}


}

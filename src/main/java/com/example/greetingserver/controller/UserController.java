package com.example.greetingserver.controller;

import com.example.greetingserver.pojo.User;
import com.example.greetingserver.pojo.UserRoles;
import com.example.greetingserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@EnableAutoConfiguration
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping("/addUser")
    public String addUserUI(){
        return "addUser";
    }



    @PostMapping (path = "/doAddUser")
    public String addUser(@RequestParam("userName") String username,
                          @RequestParam("nickName") String nickname,
                          @RequestParam("password") String password,
                          @RequestParam("file")MultipartFile file,
                          Model model) throws IOException {
        User user = new User();
        String warningMsg = null;
        int userOKtoAdd = userService.checkUniqueUsername(username);
        int nickOKtoAdd = userService.checkUniqueNickname(nickname);


        byte data[] = this.readIconFile(file);
        if((userOKtoAdd ==-1)||(nickOKtoAdd==-1)){
            warningMsg = "操作出错,请稍后再试";
        }else if((userOKtoAdd ==0)||(nickOKtoAdd==0)){
            warningMsg = "用户名或昵称已存在";
        }

        if(warningMsg!=null){
            model.addAttribute("warningMsg",warningMsg);
            return "warningPage";
        }
        user.setUsername(username);
        user.setNickname(nickname);
        user.setPassword(password);
       // userMapper.addUser(user);
        int userid = userService.addUser(user);
        if(userid>0){
            user.setId(userid);
            user.setIcon(data);
            userService.insertIcon(user);
        }
//        Map<String,String > parm = new HashMap<String,String>();
//        parm.put("V1",username);
//        parm.put("V2",nickname);
//        userService.addUser(parm);
//        String addFlag = parm.get("V3");
//        System.out.println(addFlag);
        return "addUser";
    }

    @RequestMapping("/list" )
    public String listUsers(Model model) {
        //List<User> users = userMapper.findAll();
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "listUser";
    }

    @RequestMapping("/listUserRoles" )
    public String listUserRoles(@RequestParam("username")String username,Model model) {
        //List<User> users = userMapper.findAll();
        List<UserRoles> roles = userService.getUserRoles(username);
        return "redirect:./list";
    }

    @RequestMapping("/listUpdate" )
    public String listUpdateUsers(Model model) {
        //List<User> users = userMapper.findAll();
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "listUpdateUser";
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping (path = "/doInsertIcon")
    public String insertIcon(@RequestParam("file")MultipartFile file,@RequestParam("id")String id) {
        try {
            int intId = Integer.parseInt(id);
            byte data[] = this.readIconFile(file);
            User user=new User();
            user.setId(intId);
            user.setIcon(data);
            userService.insertIcon(user);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "redirect:/user/listUpdate";

    }

    @PostMapping (path = "/doShowOneUserInfo")
    public String showOneUserInfo(@RequestParam("username")String username,Model model) {
        String warningMsg = null;
        User user =userService.getUserInfoByName(username);
        if(user!=null) {
            model.addAttribute("userInfo",user);
            return "oneUserInfo";
        }else {
            warningMsg = "打开详情页面失败";
            model.addAttribute("warningMsg", warningMsg);
            return "warningPage";
        }
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping (path = "/doDeleteUser")
    public String deleteUser(@RequestParam("username")String username,Model model) {
        String warningMsg = null;
        if(userService.deleteUser(username)==1) {
            return "redirect:/user/listUpdate";
        }else {
            warningMsg = "删除失败";
            model.addAttribute("warningMsg", warningMsg);
            return "warningPage";
        }
    }

    private byte[] readIconFile(MultipartFile file) throws IOException {
        InputStream ins = file.getInputStream();
        byte[] buffer=new byte[1024];
        int len=0;
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        while((len=ins.read(buffer))!=-1){
            bos.write(buffer,0,len);
        }
        bos.flush();
        byte data[] = bos.toByteArray();
        return data;
    }

    @RequestMapping("/login")
    public String loginUI(@RequestParam(value = "error", required = false) Boolean haserror, Model model){

        if( (haserror != null) && haserror)
            model.addAttribute("error", true);
        else
            model.addAttribute("error", false);

        return "login";
    }

    //现在没有用到这个
    @PostMapping (path = "/doLogin")
    public String doLogin(@RequestParam("userName") String username,
                          Model model,
                          HttpServletRequest request) throws IOException {
        User user = new User();
        String warningMsg = null;
        //要加判断user存不存在
        User userInfo = userService.getUserByName(username);
        if(userInfo.getId()!=0){
            request.getSession().setAttribute("UserInfo", userInfo);
            return "redirect:/hello";
        }else if(userInfo.getId()==0){
            warningMsg = "用户不存在";
        }

        if(warningMsg!=null){
            model.addAttribute("warningMsg",warningMsg);
            return "warningPage";
        }
        return "login";
    }

    @GetMapping(path = "/postLogin")
    public String postLogin(@RequestParam(value = "userName", required = false) String username,
                            @RequestParam(value = "password", required = false) String password,
                            Model model,
                            HttpServletRequest request) throws IOException {
//       子颉：这里加一些登陆后，"hello"之前你想做的任何事
//redirect 和 直接 hello的区别是所有在HelloController里的UserInfo操作都没了， hello页面
        //里需要的Nickname也没了。还是用回redirect
        return "redirect:/hello";
        //return "/hello";
    }
}

package com.example.greetingserver.controller;

import com.example.greetingserver.pojo.GroupMemberQuery;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@EnableAutoConfiguration
@RestController
@RequestMapping("/test")

public class TestController {
    @PostMapping(value="/call")
    public Map<String, Object> call(@RequestBody GroupMemberQuery dto) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", dto.getId());
        map.put("name", dto.getName());
        return map;
    }

    @RequestMapping("/test")
    public String testUI(){
        System.out.println("test");
        return "test";
    }
}

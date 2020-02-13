package com.example.demo.dao;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
public class sql {
    @GetMapping("test")
    @ResponseBody
    public String test(){
        return "hello";
    }
}

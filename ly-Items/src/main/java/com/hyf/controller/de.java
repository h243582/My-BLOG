package com.hyf.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import entity.Result;

@RestController
public class de {
    @RequestMapping("/h")
    public String ytt(){
        Result r = new Result();
        r.setMessage("hyf");
        System.out.println(r);
        System.out.println("123");
        return "123456213123";
    }

    @GetMapping("/he")
    public String yt2t(){


        return "121321";
//        return r.toString();
    }
}

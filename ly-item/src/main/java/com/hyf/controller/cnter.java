package com.hyf.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class cnter {

    @RequestMapping("/h")
    public String ytt(){
        return "sadadas";
    }
}
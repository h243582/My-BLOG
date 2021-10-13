package com.heyufei.article.controller;

import com.heyufei.article.pojo.Comment;
import com.heyufei.article.service.CommentService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;


    /**
     * 添加评论
     */
    @RequestMapping(method= RequestMethod.POST)
    public Result save(@RequestBody Comment comment){
        commentService.add(comment);
        return new Result(true, StatusCode.OK, "添加成功 ");
    }

    @RequestMapping(value="/{articleid}",method= RequestMethod.GET)
    public Result findByArticleid(@PathVariable String articleid){
        return new Result(true, StatusCode.OK, "查询成功",commentService.findByArticleid(articleid));
    }

    @GetMapping
    public Result findAll(){
        return new Result(true, StatusCode.OK, "查询全部成功",commentService.findAll());
    }
}
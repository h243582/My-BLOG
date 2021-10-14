package com.heyufei.controller;

import com.heyufei.pojo.Article;
import com.heyufei.service.ArticleSearchService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleSearchController {

    @Autowired
    private ArticleSearchService articleSearchService;


    //添加一个文章
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article){
        articleSearchService.add(article);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    /**
     * 文章搜索
     */
    @RequestMapping(value = "/search/{keywords}/{page}/{size}",method = RequestMethod.GET)
    public Result findByTitleLike(@PathVariable String keywords,@PathVariable int page,@PathVariable int size){
        Page<Article> articles = articleSearchService.findByTitleLike(keywords, page, size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Article>(articles.getTotalElements(),articles.getContent()));
    }
}

package com.heyufei.service;

import com.heyufei.dao.ArticleSearchDao;
import com.heyufei.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleSearchService {
    @Autowired
    private ArticleSearchDao articleSearchDao;

    /**
     * 增加文章
     */
    public void add(Article article){
        articleSearchDao.save(article);
    }

    public Page<Article> findByTitleLike(String keywords, int page, int size){
        PageRequest pageRequest = PageRequest.of(page-1, size);
        return articleSearchDao.findByTitleLikeOrContentLike(keywords,keywords,pageRequest);
    }
    //查询所有文章
    public List<Article> findAll(){
        return (List<Article>) articleSearchDao.findAll();
    }
}

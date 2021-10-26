package com.heyufei.article.service;

import com.heyufei.article.dao.CommentDao;
import com.heyufei.article.pojo.Article;
import com.heyufei.article.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private IdWorker idWorker;

    public void add(Comment comment){
        comment.set_id( idWorker.nextId()+"" );
        commentDao.save(comment);
    }

    public List<Comment> findByArticleid(String articleid){
        return commentDao.findByArticleid(articleid);
    }

    /**
     * 查询全部列表
     * @return
     */
    public List<Comment> findAll() {
        return commentDao.findAll();
    }
}
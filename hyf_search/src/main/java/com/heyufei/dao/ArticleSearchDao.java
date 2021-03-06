package com.heyufei.dao;

import com.heyufei.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 文章数据访问层接口
 */
public interface ArticleSearchDao extends ElasticsearchRepository<Article,String> {
    /**
     * 检索
     * @param
     * @return
     */
    public Page<Article> findByTitleLikeOrContentLike(String title, String content, Pageable pageable);








}
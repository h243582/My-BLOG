package com.heyufei.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.heyufei.article.pojo.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{
    /**
     * 审核
     * Modifying删改操作只要是设计线程安全的都需要加，service层要加事务
     * ?1表示参数列表中第一个
     */
    @Modifying
    @Query(value = "update hyf_article.tb_article set state =1 where id=?1",nativeQuery = true)
    public void examine(String id);

    /**
     * 文章点赞
     */
    @Modifying
    @Query(value = "update hyf_article.tb_article set thumbup = thumbup+1 where id=?1",nativeQuery = true)
    public int updateThumbup(String id);
}

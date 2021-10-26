package com.heyufei.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.heyufei.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{
    /**
     * 最新问题列表
     * 根据标签ID查询最新问题列表   labelid标签ID   tb_problem.replytime回复日期
     */
    @Query(value = "select * from hyf_qa.tb_problem ,hyf_qa.tb_pl where tb_problem.id=tb_pl.problemid and tb_pl.labelid =? ORDER BY tb_problem.replytime desc", nativeQuery = true )
    public Page<Problem> newList(String labelId, Pageable pageable);

    /**
     * 热门问答列表
     * 根据标签ID查询热门问答列表   labelid标签ID    reply回复数，越多越热门
     */
    @Query(value = "select * from hyf_qa.tb_problem ,hyf_qa.tb_pl where tb_problem.id=tb_pl.problemid and tb_pl.labelid =? ORDER BY tb_problem.reply desc",nativeQuery = true )
    public Page<Problem> hotList(String labelid, Pageable pageable);

    /**
     * 等待回答列表
     * 根据标签ID  和创建时间降序  查询等待回答列表 labelid标签ID    tb_problem.createtime创建时间
     */
    @Query(value = "select * from hyf_qa.tb_problem ,hyf_qa.tb_pl where tb_problem.id=tb_pl.problemid and tb_pl.labelid =? AND tb_problem.reply=0 or tb_problem.reply is null ORDER BY tb_problem.createtime desc",nativeQuery = true )
    public Page<Problem> waitList(String labelid, Pageable pageable);
}

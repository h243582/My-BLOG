package com.heyufei.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.heyufei.recruit.pojo.Recruit;

import java.util.List;

/**
 * 招聘职位接口
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{
    /**
     * 查询推荐职位列表(按创建日期降序排序)
     * state 状态文本  0关闭  1开启  2推荐      查询前四条
     */
    public List<Recruit> findTop4ByStateOrderByCreatetimeDesc(String state);

    /**
     * 最新职位列表
     * @param state   //状态>2的所有取12个
     *           根据时间倒叙OrderByCreatetimeDesc
     */
    public List<Recruit> findTop12ByStateNotOrderByCreatetimeDesc(String state);


}

package com.heyufei.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.heyufei.recruit.pojo.Enterprise;

import java.util.List;

/**
 * 企业列表接口
 * @author Administrator
 *
 */
public interface EnterpriseDao extends JpaRepository<Enterprise,String>,JpaSpecificationExecutor<Enterprise>{
    /**
     * 根据热门状态获取企业列表  0不热门 1热门
     * @param ishot
     * @return
     */
    public List<Enterprise> findByIshot(String ishot);







}

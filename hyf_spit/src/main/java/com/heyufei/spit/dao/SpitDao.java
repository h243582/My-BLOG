package com.heyufei.spit.dao;

import com.heyufei.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 吐槽数据访问层
 *
 */
public interface SpitDao extends MongoRepository<Spit,String> {
    /**
     * 根据上级id查询吐槽列表（分页)
     * @param parentid
     * @param pageable
     * @return
     */
    public Page<Spit> findByParentid(String parentid,Pageable pageable);




}

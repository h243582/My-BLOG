package com.heyufei.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.heyufei.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{
    /**
     * 根据手机号查询用户
     */
    public User findByMobile(String mobile);

    /**
     * 更新粉丝数
     */
    @Modifying
    @Query(value = "update tb_user set fanscount = fanscount + ?2 where id=?1",nativeQuery = true)
    public void incFanscount(String userid,int x);

    /**
     * 变更关注数
     */
    @Modifying
    @Query(value = "update tb_user set followcount = followcount + ?2 where id=?1",nativeQuery = true)
    public void incFollowcount(String userid,int x);

}

package com.heyufei.friend.dao;

import com.heyufei.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface FriendDao extends JpaRepository<Friend,String> {

    /**
     * 判断某个用户是不是已经有这个好友了
     */
    @Query(value = "select count(*) from tb_friend where userid = ? and friendid= ?",nativeQuery = true)
    public int selectCount(String userid,String friendid);

    /**
     * 修改friend表的所有值
     */
    @Modifying
    @Query(value = "update tb_friend set islike=?1 where userid=?2 and friendid=?3",nativeQuery = true)
    public void updateLike(String islike,String userid,String friend);
    /**
     * 删除好友
     */
    @Modifying
    @Query(value = "delete from tb_friend where userid = ?1 and friendid =?2 ",nativeQuery = true)
    public void deleteFriend(String userid,String friendid);


}

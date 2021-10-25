package com.heyufei.friend.dao;

import com.heyufei.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface FriendDao extends JpaRepository<Friend,String> {

    /**
     * 判断某个用户是不是已经有这个好友了
     */
    @Query(value = "select count(*) from tb_friend where userid = ?1 and friendid= ?2",nativeQuery = true)
    public int selectCount(String userid,String friendid);

    /**
     * 根据 用户id 和 朋友id 修改是否喜欢
     */
    @Modifying
    @Query(value = "update tb_friend set islike=(:islike) where userid=(:userid) and friendid=(:friendid)",nativeQuery = true)
    public void updateLike(@Param("islike") Integer islike,@Param("userid") String userid,@Param("friendid") String friendid);


    /**
     * 删除好友
     */
    @Modifying
    @Query(value = "delete from tb_friend where userid = ?1 and friendid =?2 ",nativeQuery = true)
    public void deleteFriend(String userid,String friendid);

    @Modifying
    @Query(value = "insert into tb_friend(userid, friendid, islike) values (?1, ?2, ?3)",nativeQuery = true)
    public void addFriend(String userid,String friendid,Integer islike);
}

package com.heyufei.friend.service;

import com.heyufei.friend.client.UserClient;
import com.heyufei.friend.dao.FriendDao;
import com.heyufei.friend.dao.NoFriendDao;
import com.heyufei.friend.pojo.Friend;
import com.heyufei.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FriendService {
    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    @Autowired
    private UserClient userClient;


    @Transactional //要么方法全部执行成功，所有sql语句全部正确执行，要么全部不做
    public int addFriend(String userid,String friendid){

        //如果已添加这个好友，则不进行任何操作,返回0
        //如果 friend 表的 userid 行包括了这个 friendid，就啥也不干 直接返回
        if(friendDao.selectCount(userid, friendid)>0){
            return 0;
        }

        //向喜欢表中添加记录
        Friend friend=new Friend(userid, friendid,0);
        friendDao.save(friend);
        userClient.attention(userid,1);//增加自己的关注数
        userClient.fans(friendid,1);//增加对方的粉丝数


        //判断对方是否喜欢你，如果喜欢，将islike设置为1
        if(friendDao.selectCount( friendid,userid)>0){
            friendDao.updateLike(1,userid,friendid);
            friendDao.updateLike(1,friendid,userid);
        }
        return 1;
    }

    /**
     * 向不喜欢列表中添加记录
     * @param userid
     * @param friendid
     */
    public void addNoFriend(String userid,String friendid){
        NoFriend noFriend=new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
    }

    /**
     * 删除好友
     * @param userid
     * @param friendid
     */
    @Transactional
    public void deleteFriend(String userid,String friendid){
        friendDao.deleteFriend(userid,friendid);
        friendDao.updateLike(0,friendid,userid);
        addNoFriend(userid,friendid);//向不喜欢表中添加记录

        userClient.attention(userid,-1);//减少自己的关注数
        userClient.fans(friendid,-1);//减少对方的粉丝数
    }




}


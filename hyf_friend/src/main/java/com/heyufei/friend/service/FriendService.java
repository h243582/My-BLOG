package com.heyufei.friend.service;

import com.heyufei.friend.dao.FriendDao;
import com.heyufei.friend.pojo.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FriendService {
    @Autowired
    private FriendDao friendDao;

    @Transactional //要么方法全部执行成功，所有sql语句全部正确执行，要么全部不做
    public int addFriend(String userid,String friendid){

        //如果已添加这个好友，则不进行任何操作,返回0
        //如果 friend 表的 userid 行包括了这个 friendid
        if(friendDao.selectCount(userid, friendid)>0){
            return 0;
        }

        //向喜欢表中添加记录
        Friend friend=new Friend(userid, friendid,"0");
        friendDao.save(friend);

        //判断对方是否喜欢你，如果喜欢，将islike设置为1
        if(friendDao.selectCount( friendid,userid)>0){
            friendDao.updateLike(userid,friendid,"1");
            friendDao.updateLike(friendid,userid,"1");
        }
        return 1;
    }
}


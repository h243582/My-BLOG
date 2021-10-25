package com.heyufei.friend.dao;

import com.heyufei.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 不喜欢列表数据访问层
 */
public interface NoFriendDao extends JpaRepository<NoFriend,String> {
}

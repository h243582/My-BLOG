package com.heyufei.friend.client;

import com.heyufei.friend.client.impl.UserClientImpl;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户客户端
 */
@FeignClient(value = "hyf-user", fallback = UserClientImpl.class)
public interface UserClient {
    /**
     * 增加粉丝数
     */
    @RequestMapping(value="/user/fans/{userid}/{x}",method= RequestMethod.POST)
    public Result fans(@PathVariable("userid") String userid, @PathVariable("x") int x);


    /**
     * 增加关注数
     */
    @RequestMapping(value="/user/attention/{userid}/{x}",method=RequestMethod.POST)
    public Result attention(@PathVariable("userid") String userid,@PathVariable("x") int x);
}

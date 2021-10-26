package com.heyufei.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户客户端
 */
@FeignClient("hyf-user")
public interface UserClient {
    /**
     * 增加粉丝数
     */
    @RequestMapping(value="/user/fans/{userid}/{x}",method= RequestMethod.POST)
    public void fans(@PathVariable("userid") String userid, @PathVariable("x") int x);


    /**
     * 增加关注数
     */
    @RequestMapping(value="/user/attention/{userid}/{x}",method=RequestMethod.POST)
    public void attention(@PathVariable("userid") String userid,@PathVariable("x") int x);
}

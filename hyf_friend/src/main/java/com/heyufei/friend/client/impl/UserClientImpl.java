package com.heyufei.friend.client.impl;

import com.heyufei.friend.client.UserClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

@Component

public class UserClientImpl implements UserClient {

    @Override
    public Result fans(String userid, int x) {
        return new Result(false, StatusCode.ERROR, "调用user微服务失败了，但是熔断器启动了","是不是服务没启动啊，猪脑壳");

    }

    @Override
    public Result attention(String userid, int x) {
        return new Result(false, StatusCode.ERROR, "调用user微服务失败了，但是熔断器启动了","是不是服务没启动啊，猪脑壳");
    }

}

package com.heyufei.qa.client.impl;

import com.heyufei.qa.client.LabelClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

@Component
public class LabelClientImpl implements LabelClient {
    @Override
    public Result findById(String id) {
        return new Result(false, StatusCode.ERROR, "虽然出错了，但是熔断器启动了","是不是服务没启动啊，猪脑壳");
    }
}
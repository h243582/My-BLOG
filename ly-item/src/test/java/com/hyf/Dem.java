package com.hyf;

import com.hyf.pojo.Brand;
import com.hyf.service.Brand2Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;

import java.util.List;

@SpringBootTest
public class Dem {
    @Autowired
    private Brand2Service brandService;

    @Test
    public void demo(){
        List<Brand> list = brandService.findAll();
        System.out.println(list);
    }
}

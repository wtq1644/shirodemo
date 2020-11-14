package com.mymc.shirodemo;

import com.mymc.shirodemo.pojo.User;
import com.mymc.shirodemo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShirodemoApplicationTest {

    @Autowired
    UserServiceImpl userService;

    @Test
    void contextLoads(){
        User user=userService.queryUserByName("aaa");
        System.out.println(user.getUsername());
        System.out.println("======在这里");
    }

}
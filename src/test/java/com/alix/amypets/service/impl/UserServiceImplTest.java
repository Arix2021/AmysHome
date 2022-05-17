package com.alix.amypets.service.impl;

import com.alix.amypets.bean.user.User;
import com.alix.amypets.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    void login() {
        userService.login("jack123","j123","");
    }

    @Test
    void reg() {
        User user = userService.reg("jack123", "j123", "");
        System.out.println(user);
    }
}
package com.alix.amypets.service.impl;

import com.alix.amypets.bean.user.User;
import com.alix.amypets.bean.zone.Dynamic;
import com.alix.amypets.service.DynamicService;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DynamicServiceImplTest {

    User user = new User();
    {
        user.setId(2);
        user.setUsername("alix123");
    }

    @Autowired
    private DynamicService dynamicService;

    @Test
    void getByDyid() {
        dynamicService.getByDyid(1,user);
    }

    @Test
    void listDynamic() {
        Dynamic condition = new Dynamic();
//        condition.setTop(true);

        // 查所有人
//        dynamicService.listDynamic(condition,user).forEach(System.out::println);

        // 查自己
        condition.setUid(2);
        dynamicService.listDynamic(condition,user).forEach(System.out::println);
    }

    @Test
    void insert() {
        Dynamic dynamic = new Dynamic();
        dynamic.setUid(user.getId());
        dynamic.setState(false);
        dynamic.setContent("这是第3条动态");
        dynamic.setCreatedTime(new Date());
        dynamic.setCreator(user.getUsername());
        dynamicService.insert(dynamic,user);
    }

    @Test
    void delete() {
        dynamicService.delete(7,user);
    }
}
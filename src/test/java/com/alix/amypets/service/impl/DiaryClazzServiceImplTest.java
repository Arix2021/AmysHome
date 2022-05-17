package com.alix.amypets.service.impl;

import com.alix.amypets.bean.user.User;
import com.alix.amypets.bean.zone.DiaryClazz;
import com.alix.amypets.service.DiaryClazzService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DiaryClazzServiceImplTest {

    @Autowired
    private DiaryClazzService service;

    @Test // 根据用户查
    void getAll() {
        User user = new User();
        user.setId(2);
        List<DiaryClazz> clz = service.getAll(2,user);
    }

    @Test // 根据分类id查
    public void getByCid(){
        service.getByCid(6);
    }

    @Test
    void getPage() {
    }

    @Test
    void insert() {
        DiaryClazz newCls = new DiaryClazz();
        newCls.setClazz("爱宠日记");
        newCls.setUid(2);
        service.insert(newCls,2);
    }

    @Test
    void update() {
        DiaryClazz cls = new DiaryClazz();
        cls.setClazz("爱宠日常");
        cls.setCid(7);
        cls.setUid(2);
        service.update(cls,2);
    }

    @Test
    void delete() {
        service.delete(3,2);
    }

    @Test // 获取分类并统计数量
    public void getClazzAndNum(){
        User user = new User();
        user.setId(2);
        service.getClazzAndNum(2,user);
    }
}
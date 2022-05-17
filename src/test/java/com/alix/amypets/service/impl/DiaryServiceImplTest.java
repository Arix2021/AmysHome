package com.alix.amypets.service.impl;

import com.alix.amypets.bean.user.User;
import com.alix.amypets.bean.zone.Diary;
import com.alix.amypets.service.DiaryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DiaryServiceImplTest {

    @Autowired
    private DiaryService diaryService;

    /**
     * uid 2 代表自己
     */

    @Test
    void getByDid() {
        // 查看自己日记
//        System.out.println(diaryService.getByDid(6,2)); // 公开的
        User user = new User();
        user.setId(2);
        System.out.println(diaryService.getByDid(5,user)); // 不公开的

        // 查看别人日记
//        System.out.println(diaryService.getByDid(6,1)); // 公开的
//        System.out.println(diaryService.getByDid(5,1)); // 不公开的 ==>> null
    }

    @Test // 查看个人日记列表
    void listByCondition() {
        Diary condition = new Diary();
        condition.setUid(2); // 要看哪个用户的文章
        condition.setTop(true); // 置顶优先
        User user = new User();
        user.setId(2);
        // 查看自己
        List<Diary> diaries = diaryService.listByCondition(condition, user);
        for (Diary diary : diaries) {
            System.out.println(diary);
        }

        // 查看别人
//        for (Diary diary : diaryService.listByCondition(condition,1)) {
//            System.out.println(diary);
//        }

    }

    @Test // 新增
    public void insert(){
        Diary diary = new Diary();
        diary.setUid(2);
        diary.setZid(2);
        diary.setCid(3);
        diary.setState(true);
        diary.setTitle("新增新增新增新增");
        diary.setContent("测试新增的日记测试新增的日记测试新增的日记测试新增的日记");
        Date date = new Date();
        diary.setCreatedTime(date);
        diary.setModifiedTime(date);
        diary.setCreator("alix123");
        diary.setModifier("alix123");

        User user = new User();
        user.setId(2);
        diaryService.insert(diary,user);
    }

    @Test // 查看所有用户日记列表
    public void listByCondition2(){
        User user = new User();
        user.setId(2);
        Diary condition = new Diary();
        diaryService.listByCondition(condition, user).forEach(System.out::println);
    }

    @Test
    void update() {
        Diary diary = new Diary();
        diary.setId(6);
        diary.setUid(2);

        //        diary.setTitle("该吃饭了");
//        System.out.println(diaryService.updateByDid(diary, 2)); // 更新自己的

        diary.setTitle("吃饭吃饭吃饭");
        User user = new User();
        user.setId(1);
        System.out.println(diaryService.updateByDid(diary, user)); // 更新别人的 ==>> 失败
    }

    @Test
    void deleteByDid() {
        User user = new User();
        user.setId(2);

//        diaryService.deleteByDid(8,user);// 删自己

        user.setId(1);
        diaryService.deleteByDid(11,user);// 删别人 ==>> 失败

    }
}
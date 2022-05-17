package com.alix.amypets.mapper;

import com.alix.amypets.bean.zone.Diary;
import com.alix.amypets.bean.zone.DiaryClazz;
import com.alix.amypets.uitls.MpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class DiaryMapperTest {

    @Autowired
    private DiaryMapper diaryMapper;

    private final MpUtil<Diary> mpUtil = new MpUtil<>();

    @Test // 通过日记的id获取日记以及它的分类
    public void testGetByDid(){
        Diary diary = diaryMapper.getByDid(2);
        System.out.println(diary);
    }

    @Test // 测试更新 （不更新分类）
    public void testUpdate(){
        Diary diary = new Diary();
        diary.setId(1);
        diary.setContent("以后能够赚很多钱，在一个静谧的海边，盖一座富丽堂皇的豪宅，" +
                "养一只拉布拉多犬， 以及一只黄金猎犬，每天过着悠闲的生活，无忧无虑，" +
                "和狗儿们相处的每一个片段，都深刻的保存在我的记意里， 他们不只是一只狗，" +
                "他们更是我的朋友，我会永远记得他们，期待下次的相遇。");
        diaryMapper.updateById(diary);
    }

    @Test // 测试更新 （更新分类）
    public void testUpdateAndClazz(){
        Diary diary = new Diary();
        diary.setId(1);
        diary.setTitle("我最向往的生活");
        diary.setCid(2);
        diaryMapper.updateById(diary);
    }

    @Test
    public void testListByUid(){
        Diary diary = new Diary();
//        diary.setUid(2);
        diary.setState(true);
        diaryMapper.listByCondition(diary);
    }

    @Test // 新增日记
    public void insert(){
        Diary diary = new Diary();
        diary.setUid(2);
        diary.setZid(2);
        diary.setCid(3);
        diary.setState(true);
        diary.setTitle("测试新增的日记");
        diary.setContent("测试新增的日记测试新增的日记测试新增的日记测试新增的日记");
        Date date = new Date();
        diary.setCreatedTime(date);
        diary.setModifiedTime(date);
        diary.setCreator("alix123");
        diary.setModifier("alix123");

        diaryMapper.insert(diary);
    }
}
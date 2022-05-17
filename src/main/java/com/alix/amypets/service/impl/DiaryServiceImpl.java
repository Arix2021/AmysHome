package com.alix.amypets.service.impl;

import com.alix.amypets.bean.user.User;
import com.alix.amypets.bean.zone.Diary;
import com.alix.amypets.bean.zone.Zone;
import com.alix.amypets.mapper.DiaryMapper;
import com.alix.amypets.service.DiaryService;
import com.alix.amypets.service.ex.base.UpdateException;
import com.alix.amypets.service.ex.base.ZoneException;
import com.alix.amypets.uitls.UserUtil;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DiaryServiceImpl implements DiaryService {

    @Autowired
    private DiaryMapper diaryMapper;

    @Override
    public Diary getByDid(Integer did, User user) {
        Diary diary = null;
        if ((diary = diaryMapper.getByDid(did)) == null)
            throw new ZoneException("获取日记失败");
        if (!diary.getUid().equals(user.getId()) && !diary.isState()) // 非作者查看要判定日记是否公开
            return null;
//        if (!diary.getUid().equals(user.getId())){ // 非作者查看则增加浏览量
            diary.setViews(diary.getViews() + 1);
            if (diaryMapper.updateById(diary) < 1)
                throw new UpdateException("浏览量更新失败");
//        }
        return diary;
    }

    @Override
    public List<Diary> listByCondition(Diary condition,User user) {
        List<Diary> diaries = null;
        if (condition.getUid() == null) { // 查看列表 所有人的日记
            condition.setState(true);
            if ((diaries = diaryMapper.listByCondition(condition)).contains(null))
                throw new ZoneException("拉取所有人日记列表失败");
        } else { // 查看列表 个人的日记
            if (!condition.getUid().equals(user.getId())) // 非作者查看
                condition.setState(true);
            if ((diaries = diaryMapper.listByCondition(condition)) == null)
                throw new ZoneException("拉取个人日记列表失败");
        }
        return diaries;
    }

    @Override
    public List<Diary> listGetSimple(Integer uid, User user) {
        UserUtil.idCheck(uid,user.getId());
        return diaryMapper.listGetSimple(uid);
    }

    @Override
    public Diary insert(Diary diary,User user) {
        UserUtil.idCheck(diary.getUid(),user.getId());
        diary.setLikes(0);
        diary.setReprints(0);
        diary.setViews(0);
        diary.setCreator(user.getUsername());
        diary.setModifier(user.getUsername());
        Date date = new Date();
        diary.setCreatedTime(date);
        diary.setModifiedTime(date);
        if (diaryMapper.insert(diary) < 1)
            throw new ZoneException("添加新日记失败");
        return diary;
    }

    @Override
    public Diary updateByDid(Diary diary, User user) {
        UserUtil.idCheck(diary.getUid(),user.getId());
        diary.setModifier(user.getModifier());
        diary.setModifiedTime(new Date());
        if (diaryMapper.updateById(diary) < 1)
            throw new ZoneException("更新日记失败");
        return diary;
    }

    @Override
    public void deleteByDid(Integer did,User user) {
        Diary condition = new Diary();
        condition.setUid(user.getId());
        condition.setId(did);
        if (diaryMapper.listByCondition(condition).size() == 0)
            throw new ZoneException("删除日记时身份异常");
        if (diaryMapper.deleteById(did) < 1)
            throw new ZoneException("删除日记失败");
    }
}

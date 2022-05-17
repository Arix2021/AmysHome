package com.alix.amypets.service.impl;

import com.alix.amypets.bean.user.User;
import com.alix.amypets.bean.zone.Diary;
import com.alix.amypets.bean.zone.Dynamic;
import com.alix.amypets.bean.zone.Zone;
import com.alix.amypets.mapper.DynamicMapper;
import com.alix.amypets.service.DynamicService;
import com.alix.amypets.service.ex.base.UpdateException;
import com.alix.amypets.service.ex.base.ZoneException;
import com.alix.amypets.uitls.MpUtil;
import com.alix.amypets.uitls.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DynamicServiceImpl implements DynamicService {

    @Autowired
    private DynamicMapper dynamicMapper;

    private final MpUtil<Dynamic> mpUtil = new MpUtil<>();

    @Override
    public Dynamic getByDyid(Integer dyid, User user) {
        Dynamic dynamic = null;
        if ((dynamic = dynamicMapper.getByDyid(dyid)) == null)
            throw new ZoneException("获取动态失败");
        if (!dynamic.getUid().equals(user.getId()) && !dynamic.isState()) // 非作者查看要判定日记是否为已发布
            return null;
//        if (!diary.getUid().equals(user.getId())){ // 非作者查看则增加浏览量
            dynamic.setViews(dynamic.getViews() + 1);
            if (dynamicMapper.updateById(dynamic) < 1)
                throw new UpdateException("浏览量更新失败");
//        }
        return dynamic;
    }

    @Override
    public List<Dynamic> listDynamic(Dynamic condition, User user) {
        List<Dynamic> dynamics = null;
        if (condition.getUid() == null) { // 查看列表 所有人的日记
            condition.setState(true);
            if ((dynamics = dynamicMapper.listByCondition(condition)).contains(null))
                throw new ZoneException("拉取所有人动态列表失败");
        } else { // 查看列表 个人的日记
            if (!condition.getUid().equals(user.getId())) // 非作者查看
                condition.setState(true);
            if ((dynamics = dynamicMapper.listByCondition(condition)) == null)
                throw new ZoneException("拉取个人动态列表失败");
        }
        return dynamics;
    }

    @Override
    public void insert(Dynamic dynamic, User user) {
        UserUtil.idCheck(dynamic.getUid(), user.getId());
        dynamic.setAvatar(user.getAvatar());
        dynamic.setViews(0);
        dynamic.setLikes(0);
        dynamic.setReprints(0);
        dynamic.setCreator(user.getUsername());
        dynamic.setCreatedTime(new Date());
        if (dynamicMapper.insert(dynamic) < 1)
            throw new ZoneException("新增动态失败");
    }

    @Override
    public void delete(Integer dyid, User user) {
        Dynamic dynamic = new Dynamic();
        dynamic.setDyid(dyid);
        dynamic.setUid(user.getId());
        if (dynamicMapper.listByCondition(dynamic) == null)
            throw new ZoneException("删除动态时身份异常");
        if (dynamicMapper.deleteById(dyid) < 1)
            throw new ZoneException("删除动态失败");
    }
}

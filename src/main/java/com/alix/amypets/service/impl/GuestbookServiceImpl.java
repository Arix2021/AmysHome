package com.alix.amypets.service.impl;

import com.alix.amypets.bean.user.User;
import com.alix.amypets.bean.zone.Guestbook;
import com.alix.amypets.mapper.GuestMapper;
import com.alix.amypets.mapper.GuestbookMapper;
import com.alix.amypets.mapper.UserMapper;
import com.alix.amypets.service.GuestbookService;
import com.alix.amypets.service.ex.base.InsertException;
import com.alix.amypets.service.ex.base.ZoneException;
import com.alix.amypets.uitls.MpUtil;
import com.alix.amypets.uitls.UserUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GuestbookServiceImpl implements GuestbookService {

    @Autowired
    private GuestbookMapper guestbookMapper;

    @Autowired
    private UserMapper userMapper;

    private final MpUtil<Guestbook> mpUtil = new MpUtil<>();

    @Override
    public Guestbook getByUid(Integer uid, User user) {
        UserUtil.idCheck(uid,user.getId());
        Guestbook guestbook = null;
        // 说明用户还没有留言板
        if (guestbookMapper.selectOne(new QueryWrapper<Guestbook>().eq("uid",uid)) == null)
            guestbook = insert(uid, user);
        // 有则正常执行
        if ((guestbook = guestbookMapper.selectOne(mpUtil.queryByUid(uid))) == null)
            throw new ZoneException("留言板获取失败");
        guestbook.setCount(guestbookMapper.countByGid(guestbook.getId()));
        return guestbook;
    }

    @Override
    public Guestbook update(Guestbook guestbook, User user) {
        UserUtil.idCheck(guestbook.getUid(),user.getId());
        guestbook.setModifier(user.getUsername());
        guestbook.setModifiedTime(new Date());
        if (guestbookMapper.updateById(guestbook) < 1)
            throw new ZoneException("留言板更新失败");
        if ((guestbook = guestbookMapper.selectOne(mpUtil.queryByUid(guestbook.getUid()))) == null)
            throw new ZoneException("留言板更新时验证异常");
        return guestbook;
    }

    @Override
    public Guestbook insert(Integer uid, User user) {
        UserUtil.idCheck(uid,user.getId());
        Guestbook guestbook = new Guestbook();
        if (guestbook.getMode() == null) {
            guestbook.setMode(3);
        }
        guestbook.setUid(user.getId());
        guestbook.setCreator(user.getUsername());
        guestbook.setModifier(user.getUsername());
        Date date = new Date();
        guestbook.setCreatedTime(date);
        guestbook.setModifiedTime(date);
        if (guestbookMapper.insert(guestbook) < 1)
            throw new InsertException("留言板新增失败");
        return guestbook;
    }


}

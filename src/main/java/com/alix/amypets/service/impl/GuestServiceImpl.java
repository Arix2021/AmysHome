package com.alix.amypets.service.impl;

import com.alix.amypets.bean.Condition;
import com.alix.amypets.bean.user.User;
import com.alix.amypets.bean.zone.Guest;
import com.alix.amypets.bean.zone.Guestbook;
import com.alix.amypets.mapper.GuestMapper;
import com.alix.amypets.mapper.GuestbookMapper;
import com.alix.amypets.service.GuestService;
import com.alix.amypets.service.ex.base.ZoneException;
import com.alix.amypets.uitls.UserUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.query.MPJQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GuestServiceImpl implements GuestService {

    @Autowired
    private GuestMapper guestMapper;

    @Autowired
    private GuestbookMapper guestbookMapper;

    @Override
    public List<Guest> listByGid(Condition condition, User user) {
        Guestbook guestbook = null;
        QueryWrapper<Guestbook> guestbookQueryWrapper = new QueryWrapper<>();
        guestbookQueryWrapper.eq("uid",condition.getId());
        if ((guestbook = guestbookMapper.selectOne(guestbookQueryWrapper)) == null)
            throw new ZoneException("留言访问异常");
        UserUtil.idCheck(guestbook.getUid(),user.getId());
        Page<Guest> page = new Page<>(condition.getPageNo(), condition.getPageSize());
//        QueryWrapper<Guest> guestQueryWrapper = new QueryWrapper<>();
//        guestQueryWrapper.eq("gid",guestbook.getId()).orderByDesc("created_time");
        IPage<Guest> guestIPage = guestMapper.selectJoinPage(
                new Page<>(condition.getPageNo(), condition.getPageSize()),
                Guest.class,
                new MPJQueryWrapper<Guest>()
                        .selectAll(Guest.class)
                        .select("nickname", "avatar")
                        .leftJoin("t_user u ON t.uid = u.user_id")
                        .eq("gid", guestbook.getId())
                        .orderByDesc("created_time"));
        return guestIPage.getRecords();
    }

    @Override
    public void insert(Guest guest, User user) {
        UserUtil.idCheck(guest.getUid(),user.getId());
        guest.setCreator(user.getUsername());
        guest.setCreatedTime(new Date());
        if (guestMapper.insert(guest) < 1)
            throw new ZoneException("新增留言失败");
    }

    @Override
    public void deleteById(Integer id, User user) {
        UserUtil.idCheck(guestMapper.selectById(id).getUid(),user.getId());
        if (guestMapper.deleteById(id) < 1)
            throw new ZoneException("删除留言失败");
    }
}

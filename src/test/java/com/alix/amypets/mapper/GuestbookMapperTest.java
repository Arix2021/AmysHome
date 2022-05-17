package com.alix.amypets.mapper;

import com.alix.amypets.bean.zone.Guestbook;
import com.alix.amypets.uitls.MpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GuestbookMapperTest {

    @Autowired
    private GuestbookMapper guestbookMapper;

    private final MpUtil<Guestbook> mpUtil = new MpUtil<Guestbook>();

    @Test // 只获得留言板
    public void getByUid(){
        System.out.println(guestbookMapper.selectOne(mpUtil.queryByUid(2)));
    }

}
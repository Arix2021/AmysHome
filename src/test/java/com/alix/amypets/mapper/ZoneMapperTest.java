package com.alix.amypets.mapper;

import com.alix.amypets.bean.zone.Zone;
import com.alix.amypets.uitls.MpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ZoneMapperTest {

    @Autowired
    private ZoneMapper mapper;

    private final MpUtil<Zone> mpUtil = new MpUtil<>();

    @Test
    public void testGetByUid(){
        Zone zone = mapper.selectOne(mpUtil.queryByUid(1));
    }

}
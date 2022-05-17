package com.alix.amypets.service.impl;

import com.alix.amypets.bean.zone.Zone;
import com.alix.amypets.service.ZoneService;
import com.alix.amypets.uitls.UserUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ZoneServiceImplTest {

    @Autowired
    private ZoneService zoneService;

    @Test
    void addZone() {
        Integer uid = 2;
        String username = "alix123";
        Zone zone = new Zone();
        zone.setUid(uid);
        zone.setAutograph("还只是星海征程的开始");
        zone.setUniqueCode(UserUtil.uniqueCode("01BD0447-66C8-44D3-9A06-1C53FA4E60B9",uid+"",9));
        zone.setCreator(username);
        zone.setModifier(username);
        Date date = new Date();
        zone.setCreatedTime(date);
        zone.setModifiedTime(date);
        zoneService.addZone(zone);
    }

    @Test
    void getZoneByUid() {
        zoneService.getZoneByUid(2);
    }

    @Test
    void updateZone() {
        Zone zone = new Zone();
        zone.setZid(2);
        zone.setUid(2);
        zone.setAutograph("还只是星海征程的开始，终将迎接尽头的光芒");
        zoneService.updateZone(zone,2);
    }
}
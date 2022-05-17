package com.alix.amypets.service.impl;

import com.alix.amypets.bean.zone.DiaryClazz;
import com.alix.amypets.bean.zone.Zone;
import com.alix.amypets.mapper.ZoneMapper;
import com.alix.amypets.service.ZoneService;
import com.alix.amypets.service.ex.base.InsertException;
import com.alix.amypets.service.ex.base.ZoneException;
import com.alix.amypets.uitls.MpUtil;
import com.alix.amypets.uitls.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZoneServiceImpl implements ZoneService {

    @Autowired
    private ZoneMapper zoneMapper;

    private final MpUtil<Zone> mpUtil = new MpUtil<>();

    @Override
    public Zone addZone(Zone zone) {
        if (zoneMapper.selectOne(mpUtil.queryByUid(zone.getUid())) != null)
            throw new ZoneException("用户重复创建空间");
        if (zoneMapper.insert(zone) < 1)
            throw new ZoneException("用户创建空间失败");
        return zone;
    }

    @Override
    public Zone getZoneByUid(Integer uid) {
        Zone zone = null;
        if ((zone = zoneMapper.selectOne(mpUtil.queryByUid(uid))) == null)
            throw new ZoneException("用户拉取空间失败");
        return zone;
    }

    @Override
    public Zone updateZone(Zone zone, Integer uid) {
        UserUtil.idCheck(zone.getUid(),uid);
        if (zoneMapper.updateById(zone) < 1)
            throw new ZoneException("用户更新空间失败");
        return zone;
    }

}

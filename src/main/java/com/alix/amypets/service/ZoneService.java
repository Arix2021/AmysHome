package com.alix.amypets.service;

import com.alix.amypets.bean.zone.Zone;

public interface ZoneService {

    /**
     * 为注册用户分配新空间
     * @param zone 新用户的空间
     * @return 用户空间
     */
    Zone addZone(Zone zone);

    /**
     * 拉取空间主页 通过uid
     * @param uid 用户id
     * @return 用户的空间
     */
    Zone getZoneByUid(Integer uid);

    /**
     * 更改空间主页信息
     * @param zone 空间
     * @param uid 请求中的uid用于校验身份
     * @return 更新后的空间主页
     */
    Zone updateZone(Zone zone,Integer uid);


}

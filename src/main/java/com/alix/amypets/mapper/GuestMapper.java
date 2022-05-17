package com.alix.amypets.mapper;

import com.alix.amypets.bean.Condition;
import com.alix.amypets.bean.zone.Guest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GuestMapper extends MPJBaseMapper<Guest> {

    /**
     * 获取留言列表 通过gid
     * @return 留言列表
     */
    List<Guest> listByGid(Integer gid);

}

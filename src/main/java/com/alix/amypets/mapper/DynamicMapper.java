package com.alix.amypets.mapper;

import com.alix.amypets.bean.zone.Dynamic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DynamicMapper extends BaseMapper<Dynamic> {

    /**
     * 查询动态 根据dyid
     * @param dyid 动态的id
     * @return 动态
     */
    Dynamic getByDyid(Integer dyid);

    /**
     * 根据条件查询动态列表
     * @param condition 条件
     * @return 动态列表
     */
    List<Dynamic> listByCondition(Dynamic condition);
}

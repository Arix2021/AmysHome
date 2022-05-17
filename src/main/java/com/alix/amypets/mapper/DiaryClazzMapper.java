package com.alix.amypets.mapper;

import com.alix.amypets.bean.zone.DiaryClazz;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiaryClazzMapper extends BaseMapper<DiaryClazz> {

    /**
     * 获取分类并统计每种分类拥有的日记数量
     * @param uid 用户id
     * @return 分类
     */
    List<DiaryClazz> getClazzAndNum(Integer uid);

}

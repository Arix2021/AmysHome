package com.alix.amypets.mapper;

import com.alix.amypets.bean.base.BaseBean;
import com.alix.amypets.bean.zone.Diary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiaryMapper extends BaseMapper<Diary> {

    /**
     * 通过日记id获取日记
     * @param did 日记id
     * @return 日记
     */
    Diary getByDid(Integer did);

    /**
     * 获取所有日记 通过条件
     * @param condition 条件
     * @return 日记列表
     */
    List<Diary> listByCondition(Diary condition);

    /**
     * 获取日记简单字段 通过用户id
     * @param uid 用户id
     * @return 只有部分字段的日记
     */
    List<Diary> listGetSimple(Integer uid);

}

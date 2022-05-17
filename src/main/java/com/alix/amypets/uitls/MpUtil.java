package com.alix.amypets.uitls;

import com.alix.amypets.mapper.DiaryMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

/**
 * MybatisPlus 工具
 */
public class MpUtil<T>{

    /**
     * 通过uid查询
     * @param uid 要查询的uid
     * @return 查询器
     */
    public QueryWrapper<T> queryByUid(Integer uid) {
        return queryWrapper("uid",uid);
    }

    /**
     * MyBatisPlus查询包装器
     * @param column 查询字段
     * @param param 查询参数
     * @return 查询器
     */
    public QueryWrapper<T> queryWrapper(String column, Object param) {
        return new QueryWrapper<T>().eq(column, param);
    }

    /**
     * yBatisPlus更新包装器
     * @param column 更新字段
     * @param param 更新参数
     * @return 更新器
     */
    public UpdateWrapper<T> updateWrapper(String column,Object param) {
        return new UpdateWrapper<T>().eq(column, param);
    }

}
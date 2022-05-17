package com.alix.amypets.service;

import com.alix.amypets.bean.user.User;
import com.alix.amypets.bean.zone.Dynamic;
import org.omg.DynamicAny.DynAny;

import java.util.List;

public interface DynamicService {

    /**
     * 获取动态 通过动态的id
     * @param dyid 动态的id
     * @param user 用户校验
     * @return 动态
     */
    Dynamic getByDyid(Integer dyid, User user);

    /**
     * 获取动态的列表 通过条件
     * @param condition 条件
     * @param user 用户校验
     * @return 根据条件返回的动态列表
     */
    List<Dynamic> listDynamic(Dynamic condition, User user);

    /**
     * 增加一个新的动态
     * @param dynamic 动态
     * @param user    用户
     */
    void insert(Dynamic dynamic, User user);

    /**
     * 删除动态
     * @param dyid 动态的id
     * @param user 用户校验
     */
    void delete(Integer dyid, User user);
}

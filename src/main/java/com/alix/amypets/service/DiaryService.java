package com.alix.amypets.service;

import com.alix.amypets.bean.user.User;
import com.alix.amypets.bean.zone.Diary;

import java.util.List;

public interface DiaryService {

    /**
     *  获取日记 通过did
     * @param did 日记id
     * @param user 用户校验
     * @return 日记
     */
    Diary getByDid(Integer did, User user);

    /**
     * 获取日记列表 根据条件
     * @param condition 查询条件
     * @param user 用户校验
     * @return 日记列表
     * 非作者查 发布状态设置为true/作者查 发布状态 false
     */
    List<Diary> listByCondition(Diary condition,User user);

    /**
     * 获取日记列表 部分字段
     * @param uid 获取该用户的日记
     * @param user 用户校验
     * @return 部分字段的日记
     */
    List<Diary> listGetSimple(Integer uid,User user);

    /**
     * 新增日记
     * @param diary 日记
     * @param user 用户校验
     * @return 新增的日记
     */
    Diary insert(Diary diary,User user);

    /**
     * 更新日记 根据日记id
     * @param diary 日记
     * @param user 用户校验
     * @return 更新后日记
     */
    Diary updateByDid(Diary diary,User user);

    /**
     * 删除日记 通过did
     * @param did 删除日记id
     * @param user 用户校验
     */
    void deleteByDid(Integer did,User user);
}

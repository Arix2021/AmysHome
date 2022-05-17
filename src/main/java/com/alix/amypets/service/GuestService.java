package com.alix.amypets.service;

import com.alix.amypets.bean.Condition;
import com.alix.amypets.bean.user.User;
import com.alix.amypets.bean.zone.Guest;

import java.util.List;

public interface GuestService {

    /**
     * 获取留言列表 通过gid
     * @param user 用户校验
     * @return 留言列表
     */
    List<Guest> listByGid(Condition condition, User user);

    /**
     * 新增留言
     * @param guest 新增的留言
     * @param user 用户校验
     */
    void insert(Guest guest,User user);

    /**
     * 删除留言 通过留言的id
     * @param id 这条留言的id
     * @param user 用户校验
     */
    void deleteById(Integer id,User user);
}

package com.alix.amypets.service;

import com.alix.amypets.bean.user.User;
import com.alix.amypets.bean.zone.Guest;
import com.alix.amypets.bean.zone.Guestbook;

public interface GuestbookService {

    /**
     * 获取留言板 通过uid
     * @param uid 用户id
     * @param user 用户验证
     * @return 留言板
     */
    Guestbook getByUid(Integer uid, User user);

    /**
     * 更新留言板信息
     * @param guestbook 要更新的信息
     * @param user 用户校验
     * @return 更新后的留言板
     */
    Guestbook update(Guestbook guestbook,User user);

    /**
     * 新增留言板
     * @param uid 给该用户新增
     * @param user 用户校验
     * @return 增加成功后的
     */
    Guestbook insert(Integer uid,User user);
}

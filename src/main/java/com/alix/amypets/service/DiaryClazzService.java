package com.alix.amypets.service;

import com.alix.amypets.bean.user.User;
import com.alix.amypets.bean.zone.Diary;
import com.alix.amypets.bean.zone.DiaryClazz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DiaryClazzService {

    /**
     * 获取该用户所有日记类型
     * @return 日志类型
     * @param uid 正在查找的uid
     * @param user session中的user
     * @return 用户所有文章分类
     */
    List<DiaryClazz> getAll(Integer uid, User user);

    /**
     * 根据日记类型的id获取日记类型
     * @param cid 要获取的日及类型
     * @return 日记类型
     */
    DiaryClazz getByCid(Integer cid);

    /**
     * 获取日记分类并统计各分类所拥有日记数
     * @param uid 用户id
     * @param user 用户校验
     * @return 分类及统计
     */
    List<DiaryClazz> getClazzAndNum (Integer uid,User user);

    /**
     * 分页查询
     * @param pageable 分页器
     * @return 分页结果
     */
    Page<DiaryClazz> getPage(Pageable pageable);

    /**
     * 增加一个新的分类
     * @param clazz 新分类
     */
    void insert(DiaryClazz clazz,Integer uid);

    /**
     * 更新分类
     * @param clazz 更新的分类
     */
    void update(DiaryClazz clazz,Integer uid);

    /**
     * 删除分类
     * @param cid 分类的id
     */
    void delete(Integer cid,Integer uid);
}

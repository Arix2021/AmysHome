package com.alix.amypets.mapper;

import com.alix.amypets.bean.user.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 通过uid获取该用户的详细信息
     * @param uid 用户id
     * @return 用户及详细信息
     */
    User getInfoByUid(Integer uid);
}

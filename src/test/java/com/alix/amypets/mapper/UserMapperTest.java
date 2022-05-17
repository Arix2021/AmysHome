package com.alix.amypets.mapper;

import com.alix.amypets.bean.user.User;
import com.alix.amypets.uitls.MpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserMapperTest {

    private final MpUtil<User> mpUtil = new MpUtil<>();

    @Autowired
    private UserMapper userMapper;

    @Test // 添加新用户
    public void add(){
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setType(0);
        user.setIsDelete(0);
        userMapper.insert(user);
    }

    @Test // 获取该用户的账号信息 id
    public void testMpUtil() {
        User user = userMapper.selectById(1);
//        User user = userMapper.selectOne(mpUtil.queryWrapper("username", "alix123"));
        System.out.println(user );
    }

    @Test // 获取用户详细信息 id
    public void testGetInfoByUid(){
        User user = userMapper.getInfoByUid(1);
    }

    @Test // 获取该用户账号信息 username
    public void testGetUserByUserName(){
        User user = userMapper.selectOne(mpUtil.queryWrapper("username", "alix123"));
    }

}
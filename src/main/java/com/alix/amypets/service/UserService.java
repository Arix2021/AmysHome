package com.alix.amypets.service;

import com.alix.amypets.bean.user.User;
import org.springframework.stereotype.Service;

public interface UserService {

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @param captcha 验证码
     * @return login user
     */
    User login(String username,String password,String captcha);

    /**
     * 注册
     * @param username 用户名
     * @param password 密码
     * @param captcha 验证码
     * @return y / n
     */
    User reg(String username,String password,String captcha);

}

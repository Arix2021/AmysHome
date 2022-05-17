package com.alix.amypets.service.impl;

import com.alix.amypets.bean.user.User;
import com.alix.amypets.bean.zone.Zone;
import com.alix.amypets.mapper.UserMapper;
import com.alix.amypets.mapper.ZoneMapper;
import com.alix.amypets.service.UserService;
import com.alix.amypets.service.ZoneService;
import com.alix.amypets.service.ex.base.InsertException;
import com.alix.amypets.service.ex.base.NullParamException;
import com.alix.amypets.service.ex.base.ZoneException;
import com.alix.amypets.service.ex.user.PasswordException;
import com.alix.amypets.service.ex.user.UserExistedException;
import com.alix.amypets.service.ex.user.UserNotFoundException;
import com.alix.amypets.uitls.JsonResult;
import com.alix.amypets.uitls.UserUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private HttpSession session;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ZoneService zoneService;

    @Override // 登录
    public User login(String username,String password,String captcha){
//        if (!UserUtil.verify(captcha,session)) // 验证码
//                throw new CaptchaException("验证码错误");
        if (UserUtil.empty(username,password))
            throw new NullParamException("用户名或密码为空");
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username",username));
        if (user == null || user.getIsDelete() == 1) // 用户为null或删除状态为1表示没有该用户
            throw new UserNotFoundException("未找到该用户");
        String md5 = UserUtil.getMd5(password, user.getSalt()); // 对输入密码加密验证
        if (!UserUtil.getMd5(password, user.getSalt()).equals(user.getPassword()))
            throw new PasswordException("密码输入错误或不符合格式");
        // 只返回需要的属性
        User res = new User();
        res.setId(user.getId());
        res.setUsername(username);
        res.setNickname(user.getNickname());
        res.setAvatar(user.getAvatar());
        session.setAttribute("user",user);  // 将已登录的user放入session
        return res;
    }

    @Override // 注册
    public User reg(String username,String password,String captcha){
//        if (!UserUtil.verify(captcha,session)) { // 验证码
//                throw new CaptchaException("验证码错误");
//        }
        boolean flag = false; // 判断管理员注册
        if (username.matches("^(ad)[a-zA-Z0-9]*(123321)$")){
            username = username.replaceAll("^(ad)","");
            username = username.replaceAll("(123321)$","");
            flag = true;
            System.out.println("管理员用户请求注册：" + username);
        }
        if (UserUtil.empty(username,password))
            throw new NullParamException("用户名或密码为空");
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username",username));
        if (!(user == null || user.getIsDelete() == 1)) // 用户为null或删除状态为1表示可注册
            throw new UserExistedException("用户已存在");
        user = UserUtil.init(username,password);  // 初始化
        if (flag) user.setType(1); // 若用户是管理员注册则提升级别
        if (userMapper.insert(user) < 1) // 注册
            throw new InsertException("注册失败");
        // 为该新用户创建个人空间
        Zone zone = new Zone();
        zone.setUid(user.getId());
        zone.setUniqueCode(UserUtil.uniqueCode(user.getSalt(),user.getId()+"",9));
        zone.setCreator(username);
        zone.setModifier(username);
        Date date = new Date();
        zone.setCreatedTime(date);
        zone.setModifiedTime(date);
        if (zoneService.addZone(zone).getZid() == null)
            throw new ZoneException("为新用户注册空间异常");
        return user;
    }
}

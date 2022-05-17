package com.alix.amypets.bean.user;

import com.alix.amypets.bean.base.BaseBean;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_user")
public class User extends BaseBean<User> {

    @TableId(value = "user_id",type = IdType.AUTO)
    private Integer id; // 用户id

    private String nickname;

    private String username;

    private String password;

    private String salt; // 加密盐

    private Integer isDelete; // 是否删除

    private String avatar; // 头像

    private Integer type; // 用户类型

    @TableField(exist = false)
    private UserInfo UserInfo; // 用户的详细信息

}


package com.alix.amypets.bean.user;

import com.alix.amypets.bean.base.BaseBean;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.ibatis.annotations.Many;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Data
@TableName("t_user_info")
public class UserInfo{

    @TableId(value = "info_id",type = IdType.AUTO)
    private Integer id; // 详细信息id

    private Integer uid; // 用户id

    private String realname;

    private Integer age;

    private Integer gender;

    private String phone;

    private String email;

//    private User user; // 所属用户
}

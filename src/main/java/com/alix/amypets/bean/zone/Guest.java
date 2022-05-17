package com.alix.amypets.bean.zone;

import com.alix.amypets.bean.base.BaseCreate;
import com.alix.amypets.bean.user.User;
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
@TableName("t_zone_guest")
public class Guest extends BaseCreate<Guest> {

    @TableId(value = "guest_id",type = IdType.AUTO)
    private Integer id;

    private Integer gid;

    private Integer uid;

    private String content;

    @TableField(exist = false)
    private User user;

}

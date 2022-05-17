package com.alix.amypets.bean.zone;

import com.alix.amypets.bean.Comment;
import com.alix.amypets.bean.base.BaseCreate;
import com.alix.amypets.bean.user.User;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * 动态的bean
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("t_zone_dynamic")
public class Dynamic extends BaseCreate<Dynamic> {

    @TableId(value = "dynamic_id",type = IdType.AUTO)
    private Integer dyid;

    private Integer uid;

    private Integer zid;

    private String content;

    @TableField("`restrict`")
    private Integer restrict;

    private Integer views;

    private Integer likes;

    private Integer reprints;

    @TableField("is_top")
    private boolean top;

    @TableField("`state`")
    private boolean state;

    @TableField(exist = false)
    private String avatar;

}

package com.alix.amypets.bean.zone;

import com.alix.amypets.bean.base.BaseBean;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 日记、文章
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("t_zone_diary")
public class Diary extends BaseBean<Diary> {

    @TableId(value = "diary_id",type = IdType.AUTO)
    private Integer id;

    private Integer uid;

    private Integer zid;

    private Integer cid;

    private String title;

    private String cover;

    private String content;

    @TableField("`restrict`")
    private Integer restrict; // 阅览权限

    @TableField("`from`")
    private Integer from; // 创作来源

    private Integer views; // 浏览量

    private Integer likes; // 赞

    private Integer reprints; // 转发

    private boolean state; // 文章状态（是否为发表）

    @TableField("`is_declare`")
    private boolean declare; // 开启声明

    @TableField("`is_comm`")
    private boolean comm; // 开启评论

    @TableField("`is_appr`")
    private boolean appr; // 开启赞赏

    @TableField("`is_top`")
    private boolean top; // 开启置顶

    @TableField(exist = false)
    private DiaryClazz clazz; // 日志分类

}

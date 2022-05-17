package com.alix.amypets.bean.zone;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 日志分类
 */
@Data
@TableName("t_zone_diary_clazz")
public class DiaryClazz {

    @TableId(value = "clazz_id",type = IdType.AUTO)
    private Integer cid;

    private Integer uid; // 所属用户

    private String clazz; // 文章分类

    @TableField(exist = false)
    private Integer num; // 该分类有几篇日记
}

package com.alix.amypets.bean.zone;


import com.alix.amypets.bean.base.BaseCreate;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 日志的标签
 */
@Data
@TableName("t_zone_dynamic_tag")
public class DiaryTag {

    @TableId(value = "tag_id",type = IdType.AUTO)
    private Integer id;

    private Integer uid; // 所属用户

    private String tag;

}

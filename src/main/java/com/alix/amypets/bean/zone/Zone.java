package com.alix.amypets.bean.zone;

import com.alix.amypets.bean.base.BaseBean;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 小窝空间
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("t_zone")
public class Zone extends BaseBean<Zone> {

    @TableId(value = "zone_id",type = IdType.AUTO)
    private Integer zid;

    private Integer uid;

    private String uniqueCode; // 小窝社区唯一编码

    private String autograph; // 小窝个性签名

    private Integer views; // 浏览量

    private Integer visitor; // 访客量

}

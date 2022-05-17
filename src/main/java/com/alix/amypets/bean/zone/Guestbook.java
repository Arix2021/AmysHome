package com.alix.amypets.bean.zone;

import com.alix.amypets.bean.base.BaseBean;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("t_zone_guestbook")
public class Guestbook extends BaseBean<Guestbook> {

    @TableId(value = "guestbook_id",type = IdType.AUTO)
    private Integer id;

    private Integer uid;

    private Integer zid;

    private Integer mode;

    private String greet;

    @TableField(exist = false)
    private Integer count;
}

package com.alix.amypets.bean;

import com.alix.amypets.bean.base.BaseCreate;
import com.alix.amypets.bean.user.User;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Date;
import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("t_comment")
public class Comment extends BaseCreate<Comment> {

    @TableId(value = "comment_id",type = IdType.AUTO)
    private Integer id;

    private Integer module; // 所属模块

    @TableField("topic_id")
    private Integer topicId; // 文章id

//    private Integer pid; // 父级评论

    private Integer uid;

    @TableField(exist = false)
    private User user; // 所属用户

//    @TableField(exist = false)
//    private User toUser; // 目标评论的用户

    private String content;

//    @TableField(exist = false)
//    private List<Comment> childComments; // 如果是一级评论则拥有子评论
}

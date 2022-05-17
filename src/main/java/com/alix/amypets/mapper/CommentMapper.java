package com.alix.amypets.mapper;

import com.alix.amypets.bean.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 获取评论
     * @param module 该评论属于哪个模块
     * @param topic_id 评论的是哪个文章
     * @return 评论列表
     */
    List<Comment> listByTopicId(Integer module,Integer topic_id);

}

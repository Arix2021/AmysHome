package com.alix.amypets.service;

import com.alix.amypets.bean.Comment;
import com.alix.amypets.bean.user.User;

import java.util.List;

public interface CommentService {

    /**
     * 获取评论
     * @param module 哪个模块的评论
     * @param topicId 哪个文章的id
     * @return 改文章的评论
     */
    List<Comment> listComment(Integer module, Integer topicId);

    /**
     * 增加评论
     * @param comment 评论
     * @param user 用户校验
     */
    void  insert(Comment comment, User user);

    /**
     * 删除评论
     * @param comment 包含要删除评论的id以及自己的用户id
     * @param user 用户校验
     */
    void delete(Comment comment,User user);
}

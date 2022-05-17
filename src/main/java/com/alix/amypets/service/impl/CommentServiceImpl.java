package com.alix.amypets.service.impl;

import com.alix.amypets.bean.Comment;
import com.alix.amypets.bean.user.User;
import com.alix.amypets.mapper.CommentMapper;
import com.alix.amypets.service.CommentService;
import com.alix.amypets.service.ex.base.ZoneException;
import com.alix.amypets.uitls.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> listComment(Integer module, Integer topicId) {
        return commentMapper.listByTopicId(module,topicId);
    }

    @Override
    public void insert(Comment comment, User user) {
        UserUtil.idCheck(comment.getUid(),user.getId());
        comment.setCreator(user.getUsername());
        comment.setCreatedTime(new Date());
        if (commentMapper.insert(comment) < 1)
            throw new ZoneException("新增评论失败");
    }

    @Override
    public void delete(Comment comment, User user) {
        UserUtil.idCheck(comment.getUid(),user.getId());
        if (commentMapper.deleteById(comment.getId()) < 1)
            throw new ZoneException("删除评论失败");
    }
}

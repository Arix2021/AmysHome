package com.alix.amypets.service.impl;

import com.alix.amypets.bean.Comment;
import com.alix.amypets.bean.user.User;
import com.alix.amypets.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentServiceImplTest {
    @Autowired
    private CommentService commentService;
    private final User user = new User();
    {
        user.setId(2);
        user.setUsername("alix123");
    }

    @Test
    void listComment() {
        System.out.println(commentService.listComment(1,29));
    }

    @Test
    void insert() {
        Comment comment = new Comment();
        comment.setContent("测试1");
        comment.setModule(1);
        comment.setTopicId(29);
        comment.setUid(user.getId());
        comment.setCreator(user.getUsername());
        comment.setCreatedTime(new Date());
        commentService.insert(comment,user);
    }

    @Test
    void delete() {
        Comment comment = new Comment();
        comment.setId(6);
        comment.setUid(2);
        commentService.delete(comment,user);
    }
}
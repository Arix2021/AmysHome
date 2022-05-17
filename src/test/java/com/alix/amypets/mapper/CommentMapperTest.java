package com.alix.amypets.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommentMapperTest {

    @Autowired
    private CommentMapper mapper;

    @Test
    public void listByTopicId(){
        mapper.listByTopicId(1,29);
    }
}
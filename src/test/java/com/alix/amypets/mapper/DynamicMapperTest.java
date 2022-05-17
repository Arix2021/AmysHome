package com.alix.amypets.mapper;

import com.alix.amypets.bean.zone.Dynamic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DynamicMapperTest {

    @Autowired
    private DynamicMapper dynamicMapper;

    @Test
    void getByDyid() {
        System.out.println(dynamicMapper.getByDyid(2));
    }

    @Test
    void listByCondition() {
    }
}
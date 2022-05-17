package com.alix.amypets.mapper;

import com.alix.amypets.bean.RequestLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RequestLogMapperTest {

    /**
     * 注意 使用的是mybatis plus的mapper
     */
    @Autowired
    private RequestLogMapper mapper;

    @Test
    void getAll() {
        List<RequestLog> requestLogs = mapper.selectList(null);
    }

    @Test
    void add() {
        RequestLog log = new RequestLog();
        log.setIp("192.168.229.128");
        log.setUrl("localhost/users");
        log.setMethod("test");
        System.out.println(log + "-***********************");
        int i = mapper.insert(log);
        System.out.println(i == 1);
    }
}
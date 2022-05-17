package com.alix.amypets.service.impl;

import com.alix.amypets.bean.user.User;
import com.alix.amypets.bean.zone.Guestbook;
import com.alix.amypets.service.GuestbookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuestbookServiceImplTest {

    @Autowired
    private GuestbookService guestbookService;

    private final User user = new User();
    {
        user.setId(2);
        user.setUsername("alix123");
    }

    @Test
    void getByUid() {
        Guestbook guestbook = new Guestbook();
        guestbook.setUid(2);
        System.out.println(guestbookService.getByUid(guestbook.getUid(),user));
    }

    @Test
    void update() {
        Guestbook guestbook = new Guestbook();
        guestbook.setGreet("新更新的留言板信息");
        guestbook.setUid(2);
        System.out.println(guestbookService.update(guestbook,user));
    }
}
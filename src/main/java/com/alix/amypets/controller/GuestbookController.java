package com.alix.amypets.controller;

import com.alix.amypets.bean.zone.Guestbook;
import com.alix.amypets.service.GuestbookService;
import com.alix.amypets.uitls.JsonResult;
import com.alix.amypets.uitls.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/guestbooks")
public class GuestbookController {

    @Autowired
    private GuestbookService guestbookService;

    @GetMapping("/{uid}")
    public JsonResult getByUid(@PathVariable Integer uid, HttpServletRequest request) {
        Guestbook guestbook = guestbookService.getByUid(uid, UserUtil.getUserByRequest(request));
        return new JsonResult(JsonResult.STATE_SUCCESS,guestbook);
    }

    @PutMapping
    public JsonResult update(Guestbook guestbook,HttpServletRequest request) {
        guestbook = guestbookService.update(guestbook, UserUtil.getUserByRequest(request));
        return new JsonResult(JsonResult.STATE_SUCCESS,guestbook);
    }
}

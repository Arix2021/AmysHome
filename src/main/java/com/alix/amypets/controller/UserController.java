package com.alix.amypets.controller;

import com.alix.amypets.bean.user.User;
import com.alix.amypets.service.UserService;
import com.alix.amypets.uitls.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @PostMapping
    public JsonResult reg(String username,String password,String captcha){
        User user = userService.reg(username, password, captcha);
        return new JsonResult(JsonResult.STATE_SUCCESS,user);
    }

    @GetMapping
    public JsonResult login(String username,String password,String captcha){
        User user = userService.login(username, password, captcha);
        return new JsonResult(JsonResult.STATE_SUCCESS,user);
    }
}

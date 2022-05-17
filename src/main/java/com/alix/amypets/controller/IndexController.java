package com.alix.amypets.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class IndexController {

    @RequestMapping("/")
    public void indexDef(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 模拟错误
//        int i = 1 / 0;
        request.getRequestDispatcher("index.html").forward(request,response);
    }
}

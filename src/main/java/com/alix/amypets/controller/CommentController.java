package com.alix.amypets.controller;

import com.alix.amypets.bean.Comment;
import com.alix.amypets.service.CommentService;
import com.alix.amypets.uitls.JsonResult;
import com.alix.amypets.uitls.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{module}/{topicId}")
    public JsonResult listComment(@PathVariable Integer module,@PathVariable Integer topicId) {
        return new JsonResult(JsonResult.STATE_SUCCESS,commentService.listComment(module, topicId));
    }

    @PostMapping
    public JsonResult insert(Comment comment, HttpServletRequest request) {
        commentService.insert(comment, UserUtil.getUserByRequest(request));
        return new JsonResult(JsonResult.STATE_SUCCESS);
    }

    @DeleteMapping
    public JsonResult delete(Comment comment, HttpServletRequest request) {
        System.out.println(comment + "mark");
        commentService.delete(comment, UserUtil.getUserByRequest(request));
        return new JsonResult(JsonResult.STATE_SUCCESS);
    }
}

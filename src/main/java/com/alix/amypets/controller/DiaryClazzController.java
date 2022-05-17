package com.alix.amypets.controller;

import com.alix.amypets.bean.user.User;
import com.alix.amypets.bean.zone.Diary;
import com.alix.amypets.bean.zone.DiaryClazz;
import com.alix.amypets.service.DiaryClazzService;
import com.alix.amypets.uitls.JsonResult;
import com.alix.amypets.uitls.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("classes")
public class DiaryClazzController {

    @Autowired
    private DiaryClazzService clazzService;

    @GetMapping
    public JsonResult getAllCls(Integer uid,HttpServletRequest request) {
        User user = UserUtil.getUserByRequest(request);
        return new JsonResult(JsonResult.STATE_SUCCESS,clazzService.getAll(uid,user));
    }

    @GetMapping("/{uid}")
    public JsonResult getClazzAndNum(@PathVariable Integer uid,HttpServletRequest request) {
        List<DiaryClazz> classes = clazzService.getClazzAndNum(uid, UserUtil.getUserByRequest(request));
        return new JsonResult(JsonResult.STATE_SUCCESS,classes);
    }

    @PostMapping
    public JsonResult insert(String clazz,Integer uid,HttpServletRequest request) {
        DiaryClazz obj = new DiaryClazz();
        obj.setClazz(clazz);
        obj.setUid(uid);
        User user = UserUtil.getUserByRequest(request);
        clazzService.insert(obj,user.getId());
        return new JsonResult(JsonResult.STATE_SUCCESS);
    }

    @PutMapping
    public JsonResult update(DiaryClazz clazz,HttpServletRequest request) {
        User user = UserUtil.getUserByRequest(request);
        clazzService.update(clazz,user.getId());
        return new JsonResult(JsonResult.STATE_SUCCESS);
    }

    @DeleteMapping
    public JsonResult delete(Integer cid,HttpServletRequest request) {
        User user = UserUtil.getUserByRequest(request);
        clazzService.delete(cid,user.getId());
        return new JsonResult(JsonResult.STATE_SUCCESS);
    }
}
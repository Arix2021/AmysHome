package com.alix.amypets.controller;

import com.alix.amypets.bean.zone.Diary;
import com.alix.amypets.bean.zone.DiaryClazz;
import com.alix.amypets.service.DiaryClazzService;
import com.alix.amypets.service.DiaryService;
import com.alix.amypets.uitls.JsonResult;
import com.alix.amypets.uitls.UserUtil;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("diaries")
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    @GetMapping("/{id}")
    private JsonResult getByDid(@PathVariable Integer id, HttpServletRequest request){
        Diary diary = diaryService.getByDid(id, UserUtil.getUserByRequest(request));
        return new JsonResult(JsonResult.STATE_SUCCESS,diary);
    }

    @GetMapping
    private JsonResult listByCondition(Diary condition,HttpServletRequest request) {
        List<Diary> diaries = diaryService.listByCondition(condition, UserUtil.getUserByRequest(request));
        return new JsonResult(JsonResult.STATE_SUCCESS,diaries);
    }

    @GetMapping("/views/{id}")
    private JsonResult listGetSimple(@PathVariable Integer id,HttpServletRequest request) {
        List<Diary> diaries = diaryService.listGetSimple(id, UserUtil.getUserByRequest(request));
        return new JsonResult(JsonResult.STATE_SUCCESS,diaries);
    }

    @PostMapping
    private JsonResult insert(Diary diary,HttpServletRequest request) {
        System.out.println(diary+"test");
        Diary res = diaryService.insert(diary, UserUtil.getUserByRequest(request));
        return new JsonResult(JsonResult.STATE_SUCCESS,res.getId());
    }

    @PutMapping
    private JsonResult update(Diary diary,HttpServletRequest request) {
        Diary res = diaryService.updateByDid(diary, UserUtil.getUserByRequest(request));
        return new JsonResult(JsonResult.STATE_SUCCESS,res.getId());
    }

    @DeleteMapping("/{id}")
    private JsonResult delete(@PathVariable Integer id,HttpServletRequest request) {
        diaryService.deleteByDid(id,UserUtil.getUserByRequest(request));
        return new JsonResult(JsonResult.STATE_SUCCESS);
    }
}
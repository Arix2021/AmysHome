package com.alix.amypets.controller;

import com.alix.amypets.bean.zone.Dynamic;
import com.alix.amypets.service.DynamicService;
import com.alix.amypets.uitls.JsonResult;
import com.alix.amypets.uitls.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/dynamics")
public class DynamicController {

    @Autowired
    private DynamicService dynamicService;

    @GetMapping("{id}")
    public JsonResult getByDyid(@PathVariable Integer id, HttpServletRequest request) {
        Dynamic dynamic = dynamicService.getByDyid(id, UserUtil.getUserByRequest(request));
        return new JsonResult(JsonResult.STATE_SUCCESS,dynamic);
    }

    @GetMapping
    public JsonResult listByCondition(Dynamic condition, HttpServletRequest request) {
        List<Dynamic> dynamics = dynamicService.listDynamic(condition, UserUtil.getUserByRequest(request));
        return new JsonResult(JsonResult.STATE_SUCCESS,dynamics);
    }

    @PostMapping
    public JsonResult insert(Dynamic dynamic,HttpServletRequest request) {
        dynamicService.insert(dynamic, UserUtil.getUserByRequest(request));
        return new JsonResult(JsonResult.STATE_SUCCESS);
    }

    @DeleteMapping("/{id}")
    public JsonResult deleteByDyid(@PathVariable Integer id,HttpServletRequest request) {
        dynamicService.delete(id, UserUtil.getUserByRequest(request));
        return new JsonResult(JsonResult.STATE_SUCCESS);
    }
}

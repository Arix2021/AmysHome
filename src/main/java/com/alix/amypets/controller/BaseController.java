package com.alix.amypets.controller;

import com.alix.amypets.service.ex.base.ServiceException;
import com.alix.amypets.uitls.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 控制层基类
 */
public class BaseController {

    @ExceptionHandler({ServiceException.class})
    public JsonResult handlerException(Throwable e){
        JsonResult res = new JsonResult();
        res.setState(JsonResult.STATE_FAIL);
        res.setCode(700);
        res.setMsg(e.getMessage());
        return res;
    }
}

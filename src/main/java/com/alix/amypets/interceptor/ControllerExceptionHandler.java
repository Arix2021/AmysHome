package com.alix.amypets.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常统一处理
 */
@ControllerAdvice
public class ControllerExceptionHandler {
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @ExceptionHandler(Exception.class)
//    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e) {
//        logger.error("Request URL : {},Exception : {}",request.getRequestURL(),e);
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("error url",request.getRequestURL());
//        modelAndView.addObject("exception",e);
//        modelAndView.setViewName("error/error.html");
//        return modelAndView;
//    }
}

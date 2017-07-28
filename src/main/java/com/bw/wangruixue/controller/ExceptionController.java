package com.bw.wangruixue.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lenovo on 2017/7/21.
 * 通过使用@ControllerAdvice定义统一的异常处理类，
 * 而不是在每个Controller中逐个定义
 *
 */
@ControllerAdvice
@RequestMapping("exception")
public class ExceptionController {
    //创建一个错误的静态常量
    public static final String DEFAULT_ERROR_VIEW = "error";

    /**
     * 创建一个异常的方法
     * @return
     * @throws Exception
     */
    @RequestMapping("/hello")
    public String hello() throws Exception {
        throw new Exception("发生错误");
    }

    /**
     * @ExceptionHandler用来定义函数针对的异常类型，
     * 最后将Exception对象和请求URL映射到error.html中
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }


}

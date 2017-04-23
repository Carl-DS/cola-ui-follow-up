package com.colaui.example.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;

public class TestController {

    /**
     * 使用ModelAndView
     * @return
     */
    @RequestMapping("/main.fwd")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("message", "This is index!");
        mv.setViewName("main");
        return mv;
    }
    
    /**
     * 使用Model
     * @param model
     * @return
     */
    @RequestMapping("/index2")
    public String index2(Model model) {
        model.addAttribute("message", "This is index2!");
        return "index2";
    }
    
    /**
     * 直接返回内容
     * @return
     */
    @RequestMapping(value="/index3", method= RequestMethod.GET)
    @ResponseBody
    public String index3() {
        return "This is index3!";
    }
    
    /**
     * 直接返回对应的index4的view
     */
    @RequestMapping(value="/index4", method= RequestMethod.GET)
    public void index4() {
    }
    
    /**
     * 直接返回index5的view，注意方法后缀：Handler
     * 比较与index4的区别
     */
    @RequestMapping(value="/index5", method= RequestMethod.GET)
    public void index5Handler() {
    }
    
    /**
     * 传参方式调用
     * @param message
     * @return
     */
    @RequestMapping(value="/index6/{message}", method= RequestMethod.GET)
    public ModelAndView index6(@PathVariable String message) {
        ModelAndView view = new ModelAndView("index6");
        view.addObject("message", message);
        return view;
    }
    
    /**
     * 传参方式调用，使用Model
     * 注意方法内的Model是直接addAttribute，没有key
     * 区别index6
     * @param message
     * @param model
     * @return
     */
    @RequestMapping(value="/index7/{message}", method= RequestMethod.GET)
    public String index7(@PathVariable String message, Model model) {
        model.addAttribute(message);
        return "index6";
    }
    
    /**
     * 传参调用
     * 指定参数名称
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value="/index8/{username}/{password}")
    public ModelAndView index8(@PathVariable("username") String userName, @PathVariable("password") String password) {
        ModelAndView view = new ModelAndView("index8");
        view.addObject("userName", userName);
        view.addObject("password", password);
        return view;
    }
    
    /**
     * 使用request/response取得参数
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/index9")
    public String index9(HttpServletRequest request, HttpServletResponse response) {
        String message = request.getParameter("message");
        request.setAttribute("message", message);
        return "index9";
    }
    
    /**
     * 使用@RequestParam方式取得参数
     * @param message
     * @return
     */
    @RequestMapping(value="/index10")
    public ModelAndView index10(@RequestParam("message") String message) {
        ModelAndView view = new ModelAndView("index10");
        view.addObject("message", message);
        return view;
    }
    
    /**
     * 默认传参方式
     * @param message
     * @param model
     * @return
     */
    @RequestMapping(value="/index11")
    public String index11(String message, Model model) {
        model.addAttribute("message", message);
        return "index10";
    }
    
    /**
     * 传入PrintWriteer，也可以传入@CookieValue
     * @param out
     * @param message
     * @param model
     * @return
     */
    @RequestMapping(value="/index12")
    public String index12(PrintWriter out, String message, Model model) {
        out.println(message);
        model.addAttribute("message", message);
        return "index10";
    }
    
    /**
     * 日期参数
     * @param day
     * @return
     */
    @RequestMapping(value="/index13")
    public ModelAndView index13(@RequestParam("day") @DateTimeFormat(pattern="yyyy-MM-dd") Date day) {
        ModelAndView view = new ModelAndView("index10");
        view.addObject("message", day.toString());
        return view;
    }
    
    /**
     * 默认action
     * @param message
     * @return
     */
    @RequestMapping
    public ModelAndView index14(String message) {
        ModelAndView view = new ModelAndView("index");
        view.addObject("message", message);
        return view;
    }
    
    /**
     * 限制访问的地址，必需有参数p=1才能触发
     * @param message
     * @param model
     * @return
     */
    @RequestMapping(value="/index15/{message}", params="p=1", method= RequestMethod.GET)
    public String index15(@PathVariable("message") String message, Model model) {
        model.addAttribute("message", message);
        return "index";
    }
    
}
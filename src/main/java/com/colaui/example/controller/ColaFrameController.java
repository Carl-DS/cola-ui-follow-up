package com.colaui.example.controller;

import com.colaui.system.model.ColaMessage;
import com.colaui.system.model.ColaUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/frame")
public class ColaFrameController {

    @RequestMapping("/account/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password){
        Subject currentUser = SecurityUtils.getSubject();

        if (!currentUser.isAuthenticated()) {
            // 把用户名和密码封装为 UsernamePasswordToken 对象
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            // rememberme
            token.setRememberMe(true);
            try {
                System.out.println("1. " + token.hashCode());
                // 执行登录.
                currentUser.login(token);
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            // 所有认证时异常的父类.
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
                System.out.println("登录失败: " + ae.getMessage());
                return "redirect:/frame/account/login.html";
            }
        }

        return "redirect:/frame/main.html";
    }

    @RequestMapping("message/pull")
    @ResponseBody
    public List<ColaMessage> pull() {
        List<ColaMessage> messages = new ArrayList<ColaMessage>();
        ColaMessage message = new ColaMessage();
        message.setContent("6");
        message.setType("message");
        messages.add(message);

        message = new ColaMessage();
        message.setContent("22");
        message.setType("task");
        messages.add(message);
        return messages;
    }

    @RequestMapping("user/detail")
    @ResponseBody
    public ColaUser userDetail() {
        ColaUser user = new ColaUser();
        user.setAvatar("./resources/images/avatars/alex.png");
        return user;
    }
}

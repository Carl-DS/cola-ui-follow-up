package com.colaui.system.controller;

import com.colaui.system.model.ColaMessage;
import com.colaui.system.model.ColaUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/frame")
public class ColaFrameController {

    @PostMapping("/account/login")
    public Map<String, Object> login(@RequestBody Map<String, Object> principal){
        Map<String, Object> result = new HashMap<String, Object>();
        String username = (String) principal.get("username");
        String password = (String) principal.get("password");
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            // 把用户名和密码封装为 UsernamePasswordToken 对象
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            // rememberme
            token.setRememberMe(false);
            try {
                // 执行登录.
                currentUser.login(token);
            }
            // 所有认证时异常的父类.
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
                System.out.println("用户名或密码错误: " + ae.getMessage());
                result.put("type", 0);
                result.put("message", "用户名或密码错误");
                return result;
            }
        }
        System.out.println("登录成功!");
        result.put("type", 1);
        result.put("message", "登录成功");
        return result;
    }

    @GetMapping("message/pull")
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

    @GetMapping("user/detail")
    public ColaUser userDetail() {
        ColaUser user = new ColaUser();
        user.setAvatar("./resources/images/avatars/alex.png");
        return user;
    }
}

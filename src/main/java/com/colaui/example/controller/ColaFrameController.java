package com.colaui.example.controller;

import com.colaui.example.model.ColaMessage;
import com.colaui.example.model.ColaUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ColaFrameController {

    @RequestMapping("message/pull")
    public List<ColaMessage> pull() {
        List<ColaMessage> messages = new ArrayList<ColaMessage>();
        ColaMessage message = new ColaMessage();
        message.setContent("20");
        message.setType("message");
        messages.add(message);

        message = new ColaMessage();
        message.setContent("22");
        message.setType("task");
        messages.add(message);
        return messages;
    }

    @RequestMapping("user/detail")
    public ColaUser userDetail() {
        ColaUser user = new ColaUser();
        user.setAvatar("./resources/images/avatars/alex.png");
        return user;
    }
}

package com.colaui.system.controller;

import com.colaui.example.model.ColaUrl;
import com.colaui.system.service.ColaUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by carl.li on 2017/3/3.
 */
@RestController
@RequestMapping("url")
public class ColaUrlController {

    @Autowired
    private ColaUrlService colaUrlService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<ColaUrl> getUrl(@RequestParam(required = false) String id) {
        return colaUrlService.getUrl(id);
    }
}

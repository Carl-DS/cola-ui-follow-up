package com.colaui.system.controller;

import com.colaui.example.model.ColaUrl;
import com.colaui.system.service.ColaUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by carl.li on 2017/3/3.
 */
@RestController
@RequestMapping("frame/url")
public class ColaUrlController {

    @Autowired
    private ColaUrlService colaUrlService;

    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    public List<ColaUrl> getUrls(@RequestParam(required = false) Map<String, Object> params) {
        return colaUrlService.getUrls(params);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public void saveUrl(@RequestBody ColaUrl url) {
        colaUrlService.saveUrl(url);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteUrl(@PathVariable String id) {
        colaUrlService.deleteUrl(id);
    }
}

package com.colaui.example.controller;

import com.colaui.example.model.ColaArea;
import com.colaui.example.service.ColaAreaService;
import com.colaui.helper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by carl.li on 2017/2/14.
 */
@RestController
@RequestMapping("area")
public class ColaAreaController {
    @Autowired
    private ColaAreaService colaAreaService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page<ColaArea> getAreas(@RequestParam int pageSize, @RequestParam int pageNo) {
        return colaAreaService.getAreas(pageSize, pageNo);
    }

    @RequestMapping(value = "/recursion/", method = RequestMethod.GET)
    public List<ColaArea> recursionTree(@RequestParam String parentId) {
        return  colaAreaService.recursionTree(parentId);
    }
}

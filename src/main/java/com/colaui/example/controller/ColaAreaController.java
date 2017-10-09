package com.colaui.example.controller;

import com.colaui.example.model.ColaArea;
import com.colaui.example.service.ColaAreaService;
import com.colaui.helper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by carl.li on 2017/2/14.
 */
@RestController
@RequestMapping("/area")
public class ColaAreaController {
    @Autowired
    private ColaAreaService colaAreaService;

    @GetMapping(value = "")
    public Page<ColaArea> getAreas(@RequestParam int pageSize, @RequestParam int pageNo) {
        return colaAreaService.getAreas(pageSize, pageNo);
    }

    @GetMapping(value = "/recursion")
    public List<ColaArea> recursionTree(@RequestParam String parentId) {
        return  colaAreaService.recursionTree(parentId);
    }
}

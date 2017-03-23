package com.colaui.system.controller;

import com.colaui.example.model.ColaDept;
import com.colaui.system.service.ColaDeptService;
import com.colaui.provider.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/frame/dept")
public class ColaDeptController {
    @Autowired
    private ColaDeptService deptService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page<ColaDept> paging(@RequestParam int pageSize,
                                 @RequestParam int pageNo, @RequestParam(required=false) String contain) {
        return deptService.getPage(pageSize, pageNo, contain);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public void save(@RequestBody ColaDept dept) {
        deptService.save(dept);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) {
        deptService.delete(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public void update(@RequestBody ColaDept dept) {
        deptService.update(dept);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.GET)
    public ColaDept find(@PathVariable("id") long id) {
        return deptService.find(id);
    }

    @RequestMapping(value = "/{from}/{limit}", method = RequestMethod.GET)
    public List<ColaDept> find(@PathVariable("from") int from,
                               @PathVariable("limit") int limit) {
        return deptService.find(from, limit);
    }
}
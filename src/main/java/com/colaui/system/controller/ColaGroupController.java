package com.colaui.system.controller;

import com.colaui.example.model.ColaGroup;
import com.colaui.system.service.ColaGroupService;
import com.colaui.provider.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/frame/group")
public class ColaGroupController {
    @Autowired
    private ColaGroupService groupService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page<ColaGroup> paging(@RequestParam int pageSize,
                                  @RequestParam int pageNo, @RequestParam(required=false) String contain) {
        return groupService.getPage(pageSize, pageNo, contain);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public void save(@RequestBody ColaGroup group) {
        groupService.save(group);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") String id) {
        groupService.delete(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public void update(@RequestBody ColaGroup group) {
        groupService.update(group);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.GET)
    public ColaGroup find(@PathVariable("id") String id) {
        return groupService.find(id);
    }

    @RequestMapping(value = "/{from}/{limit}", method = RequestMethod.GET)
    public List<ColaGroup> find(@PathVariable("from") int from,
                                @PathVariable("limit") int limit) {
        return groupService.find(from, limit);
    }
}
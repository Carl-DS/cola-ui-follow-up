package com.colaui.system.controller;

import com.colaui.system.model.ColaGroup;
import com.colaui.system.service.ColaGroupService;
import com.colaui.helper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/frame/group")
public class ColaGroupController {

    @Autowired
    private ColaGroupService groupService;

    @GetMapping
    public Page<ColaGroup> paging(@RequestParam int pageSize,
                                  @RequestParam int pageNo,
                                  @RequestParam(required = false) String contain) {
        return groupService.getPage(pageSize, pageNo, contain);
    }

    @GetMapping("/rolegroups")
    public Page<ColaGroup> roleGroups(@RequestParam int pageSize,
                                      @RequestParam int pageNo,
                                      @RequestParam(required = false) String roleId) {
        return groupService.roleGroups(pageSize, pageNo, roleId);
    }

    @PostMapping
    public void save(@RequestBody ColaGroup group) {
        groupService.save(group);
    }

    @DeleteMapping("/{id}/")
    public void delete(@PathVariable("id") String id) {
        groupService.delete(id);
    }

    @PutMapping
    public void update(@RequestBody ColaGroup group) {
        groupService.update(group);
    }

    @GetMapping("/{id}/")
    public ColaGroup find(@PathVariable("id") String id) {
        return groupService.find(id);
    }

    @GetMapping("/{from}/{limit}")
    public List<ColaGroup> find(@PathVariable("from") int from,
                                @PathVariable("limit") int limit) {
        return groupService.find(from, limit);
    }
}
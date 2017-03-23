package com.colaui.system.controller;

import com.colaui.example.model.ColaRole;
import com.colaui.system.service.ColaRoleService;
import com.colaui.provider.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("role")
public class ColaRoleController {
    @Autowired
    private ColaRoleService roleService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page<ColaRole> paging(@RequestParam int pageSize,
                                 @RequestParam int pageNo, @RequestParam(required=false) String contain) {
        return roleService.getPage(pageSize, pageNo, contain);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public void save(@RequestBody ColaRole role) {
        roleService.save(role);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) {
        roleService.delete(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public void update(@RequestBody ColaRole role) {
        roleService.update(role);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.GET)
    public ColaRole find(@PathVariable("id") long id) {
        return roleService.find(id);
    }

    @RequestMapping(value = "/{from}/{limit}", method = RequestMethod.GET)
    public List<ColaRole> find(@PathVariable("from") int from,
                               @PathVariable("limit") int limit) {
        return roleService.find(from, limit);
    }
}
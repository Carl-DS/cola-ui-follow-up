package com.colaui.system.controller;

import com.colaui.example.model.ColaRoleResource;
import com.colaui.system.service.ColaRoleResourceService;
import com.colaui.provider.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/frame/roleresource")
public class ColaRoleResourceController {
    @Autowired
    private ColaRoleResourceService roleresourceService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page<ColaRoleResource> paging(@RequestParam int pageSize,
                                         @RequestParam int pageNo,
                                         @RequestParam(required=false) String contain) {
        return roleresourceService.getPage(pageSize, pageNo, contain);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public void save(@RequestBody Map<String, Object> params) {
        String roleId = (String) params.get("roleId");
        ArrayList<String> urlIds = (ArrayList<String>) params.get("urlIds");
        ArrayList<String> excludeUrlIds = (ArrayList<String>) params.get("excludeUrlIds");
        roleresourceService.saveOrDelete(roleId, urlIds, excludeUrlIds);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") String id) {
        roleresourceService.delete(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public void update(@RequestBody ColaRoleResource roleresource) {
        roleresourceService.update(roleresource);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.GET)
    public ColaRoleResource find(@PathVariable("id") String id) {
        return roleresourceService.find(id);
    }

    @RequestMapping(value = "/{from}/{limit}", method = RequestMethod.GET)
    public List<ColaRoleResource> find(@PathVariable("from") int from,
                                       @PathVariable("limit") int limit) {
        return roleresourceService.find(from, limit);
    }

}
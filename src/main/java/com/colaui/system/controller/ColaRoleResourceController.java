package com.colaui.system.controller;

import com.colaui.system.model.ColaRoleResource;
import com.colaui.system.service.ColaRoleResourceService;
import com.colaui.helper.Page;
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

    @GetMapping
    public Page<ColaRoleResource> paging(@RequestParam int pageSize,
                                         @RequestParam int pageNo,
                                         @RequestParam(required = false) String contain) {
        return roleresourceService.getPage(pageSize, pageNo, contain);
    }

    @PostMapping
    public void save(@RequestBody Map<String, Object> params) {
        String roleId = (String) params.get("roleId");
        ArrayList<String> urlIds = (ArrayList<String>) params.get("urlIds");
        ArrayList<String> excludeUrlIds = (ArrayList<String>) params.get("excludeUrlIds");
        roleresourceService.saveOrDelete(roleId, urlIds, excludeUrlIds);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        roleresourceService.delete(id);
    }

    @PutMapping
    public void update(@RequestBody ColaRoleResource roleresource) {
        roleresourceService.update(roleresource);
    }

    @GetMapping("/{id}")
    public ColaRoleResource find(@PathVariable("id") String id) {
        return roleresourceService.find(id);
    }

    @GetMapping("/{from}/{limit}")
    public List<ColaRoleResource> find(@PathVariable("from") int from,
                                       @PathVariable("limit") int limit) {
        return roleresourceService.find(from, limit);
    }

}
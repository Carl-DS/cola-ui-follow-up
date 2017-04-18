package com.colaui.system.controller;

import com.colaui.example.model.ColaDept;
import com.colaui.provider.Page;
import com.colaui.system.service.ColaDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/frame/dept")
public class ColaDeptController {
    @Autowired
    private ColaDeptService deptService;

    @RequestMapping(value = "/depts", method = RequestMethod.GET)
    public List<ColaDept> getDepts(@RequestParam(required = false) Map<String, Object> params) {
        return deptService.getDepts(params);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page<ColaDept> paging(@RequestParam int pageSize,
                                 @RequestParam int pageNo, @RequestParam(required=false) String contain) {
        return deptService.getPage(pageSize, pageNo, contain);
    }

    @RequestMapping(value = "/groupdepts/", method = RequestMethod.GET)
    public Page<ColaDept> groupDepts(@RequestParam int pageSize,
                                     @RequestParam int pageNo,
                                     @RequestParam(required=false) String groupId) {
        return deptService.groupDepts(pageSize, pageNo, groupId);
    }

    @RequestMapping(value = "/roledepts/", method = RequestMethod.GET)
    public Page<ColaDept> roleDepts(@RequestParam int pageSize,
                                     @RequestParam int pageNo,
                                     @RequestParam(required=false) String roleId) {
        return deptService.roleDepts(pageSize, pageNo, roleId);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public void save(@RequestBody ColaDept dept) {
        deptService.save(dept);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") String id) {
        deptService.delete(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public void update(@RequestBody ColaDept dept) {
        deptService.update(dept);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.GET)
    public ColaDept find(@PathVariable("id") String id) {
        return deptService.find(id);
    }

    @RequestMapping(value = "/{from}/{limit}", method = RequestMethod.GET)
    public List<ColaDept> find(@PathVariable("from") int from,
                               @PathVariable("limit") int limit) {
        return deptService.find(from, limit);
    }
}
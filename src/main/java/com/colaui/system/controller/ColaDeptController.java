package com.colaui.system.controller;

import com.colaui.system.model.ColaDept;
import com.colaui.helper.Page;
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

    @GetMapping("/depts")
    public List<ColaDept> getDepts(@RequestParam(required = false) Map<String, Object> params) {
        return deptService.getDepts(params);
    }

    @GetMapping("")
    public Page<ColaDept> paging(@RequestParam int pageSize,
                                 @RequestParam int pageNo,
                                 @RequestParam(required=false) String contain) {
        return deptService.getPage(pageSize, pageNo, contain);
    }

    @GetMapping("/groupdepts")
    public Page<ColaDept> groupDepts(@RequestParam int pageSize,
                                     @RequestParam int pageNo,
                                     @RequestParam(required=false) String groupId) {
        return deptService.groupDepts(pageSize, pageNo, groupId);
    }

    @GetMapping("/roledepts")
    public Page<ColaDept> roleDepts(@RequestParam int pageSize,
                                     @RequestParam int pageNo,
                                     @RequestParam(required=false) String roleId) {
        return deptService.roleDepts(pageSize, pageNo, roleId);
    }

    @PostMapping
    public void save(@RequestBody ColaDept dept) {
        deptService.save(dept);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        deptService.delete(id);
    }

    @RequestMapping("/")
    public void update(@RequestBody ColaDept dept) {
        deptService.update(dept);
    }

    @GetMapping("/{id}")
    public ColaDept find(@PathVariable("id") String id) {
        return deptService.find(id);
    }

    @GetMapping("/{from}/{limit}")
    public List<ColaDept> find(@PathVariable("from") int from,
                               @PathVariable("limit") int limit) {
        return deptService.find(from, limit);
    }
}
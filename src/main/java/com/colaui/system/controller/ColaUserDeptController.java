package com.colaui.system.controller;

import com.colaui.system.model.ColaUserDept;
import com.colaui.system.service.ColaUserDeptService;
import com.colaui.helper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/frame/userdept")
public class ColaUserDeptController {

    @Autowired
    private ColaUserDeptService userdeptService;

    @GetMapping
    public Page<ColaUserDept> paging(@RequestParam int pageSize,
                                     @RequestParam int pageNo,
                                     @RequestParam(required = false) String contain) {
        return userdeptService.getPage(pageSize, pageNo, contain);
    }

    @PostMapping
    public void save(@RequestBody ColaUserDept userdept) {
        userdeptService.save(userdept);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        userdeptService.delete(id);
    }

    @PutMapping
    public void update(@RequestBody ColaUserDept userdept) {
        userdeptService.update(userdept);
    }

    @GetMapping("/{id}")
    public ColaUserDept find(@PathVariable("id") long id) {
        return userdeptService.find(id);
    }

    @GetMapping("/{from}/{limit}")
    public List<ColaUserDept> find(@PathVariable("from") int from,
                                   @PathVariable("limit") int limit) {
        return userdeptService.find(from, limit);
    }
}
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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page<ColaUserDept> paging(@RequestParam int pageSize,
                                     @RequestParam int pageNo, @RequestParam(required=false) String contain) {
        return userdeptService.getPage(pageSize, pageNo, contain);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public void save(@RequestBody ColaUserDept userdept) {
        userdeptService.save(userdept);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) {
        userdeptService.delete(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public void update(@RequestBody ColaUserDept userdept) {
        userdeptService.update(userdept);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.GET)
    public ColaUserDept find(@PathVariable("id") long id) {
        return userdeptService.find(id);
    }

    @RequestMapping(value = "/{from}/{limit}", method = RequestMethod.GET)
    public List<ColaUserDept> find(@PathVariable("from") int from,
                                   @PathVariable("limit") int limit) {
        return userdeptService.find(from, limit);
    }
}
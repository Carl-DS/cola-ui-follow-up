package com.colaui.system.controller;

import com.colaui.example.model.ColaRoleMember;
import com.colaui.system.service.ColaRoleMemberService;
import com.colaui.provider.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/frame/rolemember")
public class ColaRoleMemberController {
    @Autowired
    private ColaRoleMemberService rolememberService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page<ColaRoleMember> paging(@RequestParam int pageSize,
                                       @RequestParam int pageNo, @RequestParam(required=false) String contain) {
        return rolememberService.getPage(pageSize, pageNo, contain);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public void save(@RequestBody ColaRoleMember rolemember) {
        rolememberService.save(rolemember);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) {
        rolememberService.delete(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public void update(@RequestBody ColaRoleMember rolemember) {
        rolememberService.update(rolemember);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.GET)
    public ColaRoleMember find(@PathVariable("id") long id) {
        return rolememberService.find(id);
    }

    @RequestMapping(value = "/{from}/{limit}", method = RequestMethod.GET)
    public List<ColaRoleMember> find(@PathVariable("from") int from,
                                     @PathVariable("limit") int limit) {
        return rolememberService.find(from, limit);
    }
}
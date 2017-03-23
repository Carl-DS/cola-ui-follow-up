package com.colaui.system.controller;

import com.colaui.example.model.ColaGroupMember;
import com.colaui.system.service.ColaGroupMemberService;
import com.colaui.provider.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/frame/groupmember")
public class ColaGroupMemberController {
    @Autowired
    private ColaGroupMemberService groupmemberService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page<ColaGroupMember> paging(@RequestParam int pageSize,
                                        @RequestParam int pageNo, @RequestParam(required=false) String contain) {
        return groupmemberService.getPage(pageSize, pageNo, contain);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public void save(@RequestBody ColaGroupMember groupmember) {
        groupmemberService.save(groupmember);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) {
        groupmemberService.delete(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public void update(@RequestBody ColaGroupMember groupmember) {
        groupmemberService.update(groupmember);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.GET)
    public ColaGroupMember find(@PathVariable("id") long id) {
        return groupmemberService.find(id);
    }

    @RequestMapping(value = "/{from}/{limit}", method = RequestMethod.GET)
    public List<ColaGroupMember> find(@PathVariable("from") int from,
                                      @PathVariable("limit") int limit) {
        return groupmemberService.find(from, limit);
    }
}
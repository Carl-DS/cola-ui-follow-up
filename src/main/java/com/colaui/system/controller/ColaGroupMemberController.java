package com.colaui.system.controller;

import com.colaui.example.model.ColaGroupMember;
import com.colaui.system.service.ColaGroupMemberService;
import com.colaui.provider.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public void saveGroupUser(@RequestBody Map<String, Object> params) {
        String groupId = (String) params.get("groupId");
        ArrayList<String> groupUserIds = (ArrayList<String>) params.get("groupUserIds");
        groupmemberService.saveGroupUser(groupId, groupUserIds);
    }

    @RequestMapping(value = "/position/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public void saveGroupPosition(@RequestBody Map<String, Object> params) {
        String groupId = (String) params.get("groupId");
        ArrayList<String> groupPositionIds = (ArrayList<String>) params.get("groupPositionIds");
        groupmemberService.saveGroupPosition(groupId, groupPositionIds);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) {
        groupmemberService.delete(id);
    }

    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public void deleteByUsername(@RequestParam String groupId,@RequestParam String username) {
        groupmemberService.deleteByUsername(groupId, username);
    }

    @RequestMapping(value = "/position/", method = RequestMethod.GET)
    public void deleteByPositionId(@RequestParam String groupId,@RequestParam String positionId) {
        groupmemberService.deleteByPositionId(groupId, positionId);
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

    @RequestMapping(value = "/checksame/", method = RequestMethod.GET)
    public List<ColaGroupMember> checkSame(@RequestParam String groupId, @RequestParam String username) {
        return groupmemberService.checkSame(groupId, username);
    }

    @RequestMapping(value = "/checksame/position/", method = RequestMethod.GET)
    public List<ColaGroupMember> checkSamePosition(@RequestParam String groupId, @RequestParam String positionId) {
        return groupmemberService.checkSamePosition(groupId, positionId);
    }
}
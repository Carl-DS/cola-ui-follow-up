package com.colaui.system.controller;

import com.colaui.system.model.ColaGroupMember;
import com.colaui.system.service.ColaGroupMemberService;
import com.colaui.helper.Page;
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

    @GetMapping
    public Page<ColaGroupMember> paging(@RequestParam int pageSize,
                                        @RequestParam int pageNo,
                                        @RequestParam(required = false) String contain) {
        return groupmemberService.getPage(pageSize, pageNo, contain);
    }

    @PostMapping("/user")
    public void saveGroupUser(@RequestBody Map<String, Object> params) {
        String groupId = (String) params.get("groupId");
        ArrayList<String> groupUserIds = (ArrayList<String>) params.get("groupUserIds");
        groupmemberService.saveGroupUser(groupId, groupUserIds);
    }

    @PostMapping("/position")
    public void saveGroupPosition(@RequestBody Map<String, Object> params) {
        String groupId = (String) params.get("groupId");
        ArrayList<String> groupPositionIds = (ArrayList<String>) params.get("groupPositionIds");
        groupmemberService.saveGroupPosition(groupId, groupPositionIds);
    }

    @PostMapping("/dept")
    public void saveGroupDept(@RequestBody Map<String, Object> params) {
        String groupId = (String) params.get("groupId");
        ArrayList<String> groupDeptIds = (ArrayList<String>) params.get("groupDeptIds");
        groupmemberService.saveGroupDept(groupId, groupDeptIds);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        groupmemberService.delete(id);
    }

    @GetMapping("/user")
    public void deleteByUsername(@RequestParam String groupId, @RequestParam String username) {
        groupmemberService.deleteByUsername(groupId, username);
    }

    @GetMapping("/position")
    public void deleteByPositionId(@RequestParam String groupId, @RequestParam String positionId) {
        groupmemberService.deleteByPositionId(groupId, positionId);
    }

    @GetMapping("/dept")
    public void deleteByDeptId(@RequestParam String groupId, @RequestParam String deptId) {
        groupmemberService.deleteByDeptId(groupId, deptId);
    }

    @PutMapping
    public void update(@RequestBody ColaGroupMember groupmember) {
        groupmemberService.update(groupmember);
    }

    @GetMapping("/{id}")
    public ColaGroupMember find(@PathVariable("id") long id) {
        return groupmemberService.find(id);
    }

    @GetMapping("/{from}/{limit}")
    public List<ColaGroupMember> find(@PathVariable("from") int from,
                                      @PathVariable("limit") int limit) {
        return groupmemberService.find(from, limit);
    }

    @GetMapping("/checksame/user")
    public List<ColaGroupMember> checkSameUser(@RequestParam String groupId, @RequestParam String username) {
        return groupmemberService.checkSameUser(groupId, username);
    }

    @GetMapping("/checksame/position")
    public List<ColaGroupMember> checkSamePosition(@RequestParam String groupId, @RequestParam String positionId) {
        return groupmemberService.checkSamePosition(groupId, positionId);
    }

    @GetMapping("/checksame/dept")
    public List<ColaGroupMember> checkSameDept(@RequestParam String groupId, @RequestParam String deptId) {
        return groupmemberService.checkSameDept(groupId, deptId);
    }
}
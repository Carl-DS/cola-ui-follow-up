package com.colaui.system.controller;

import com.colaui.system.model.ColaRoleMember;
import com.colaui.helper.Page;
import com.colaui.system.service.ColaRoleMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/frame/rolemember")
public class ColaRoleMemberController {

    @Autowired
    private ColaRoleMemberService rolememberService;

    @GetMapping
    public Page<ColaRoleMember> paging(@RequestParam int pageSize,
                                       @RequestParam int pageNo,
                                       @RequestParam(required = false) String contain) {
        return rolememberService.getPage(pageSize, pageNo, contain);
    }

    @PostMapping
    public void save(@RequestBody ColaRoleMember rolemember) {
        rolememberService.save(rolemember);
    }

    @PostMapping("/user")
    public void saveRoleUser(@RequestBody Map<String, Object> params) {
        String roleId = (String) params.get("roleId");
        ArrayList<String> roleUserIds = (ArrayList<String>) params.get("roleUserIds");
        rolememberService.saveRoleUser(roleId, roleUserIds);
    }

    @PostMapping("/position")
    public void saveRolePosition(@RequestBody Map<String, Object> params) {
        String roleId = (String) params.get("roleId");
        ArrayList<String> rolePositionIds = (ArrayList<String>) params.get("rolePositionIds");
        rolememberService.saveRolePosition(roleId, rolePositionIds);
    }

    @PostMapping("/dept")
    public void saveRoleDept(@RequestBody Map<String, Object> params) {
        String roleId = (String) params.get("roleId");
        ArrayList<String> roleDeptIds = (ArrayList<String>) params.get("roleDeptIds");
        rolememberService.saveRoleDept(roleId, roleDeptIds);
    }

    @PostMapping("/group")
    public void saveRoleGroup(@RequestBody Map<String, Object> params) {
        String roleId = (String) params.get("roleId");
        ArrayList<String> roleGroupIds = (ArrayList<String>) params.get("roleGroupIds");
        rolememberService.saveRoleGroup(roleId, roleGroupIds);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        rolememberService.delete(id);
    }

    @GetMapping("/user")
    public void deleteByUsername(@RequestParam String roleId, @RequestParam String username) {
        rolememberService.deleteByUsername(roleId, username);
    }

    @GetMapping("/position")
    public void deleteByPositionId(@RequestParam String roleId, @RequestParam String positionId) {
        rolememberService.deleteByPositionId(roleId, positionId);
    }

    @GetMapping("/dept")
    public void deleteByDeptId(@RequestParam String roleId, @RequestParam String deptId) {
        rolememberService.deleteByDeptId(roleId, deptId);
    }

    @GetMapping("/group")
    public void deleteByGroupId(@RequestParam String roleId, @RequestParam String groupId) {
        rolememberService.deleteByGroupId(roleId, groupId);
    }

    @PutMapping
    public void update(@RequestBody ColaRoleMember rolemember) {
        rolememberService.update(rolemember);
    }

    @GetMapping("/{id}")
    public ColaRoleMember find(@PathVariable("id") String id) {
        return rolememberService.find(id);
    }

    @GetMapping("/{from}/{limit}")
    public List<ColaRoleMember> find(@PathVariable("from") int from,
                                     @PathVariable("limit") int limit) {
        return rolememberService.find(from, limit);
    }

    @GetMapping("/checksame/user")
    public List<ColaRoleMember> checkSameUser(@RequestParam String roleId, @RequestParam String username) {
        return rolememberService.checkSameUser(roleId, username);
    }

    @GetMapping("/checksame/position")
    public List<ColaRoleMember> checkSamePosition(@RequestParam String roleId, @RequestParam String positionId) {
        return rolememberService.checkSamePosition(roleId, positionId);
    }

    @GetMapping("/checksame/dept")
    public List<ColaRoleMember> checkSameDept(@RequestParam String roleId, @RequestParam String deptId) {
        return rolememberService.checkSameDept(roleId, deptId);
    }

    @GetMapping("/checksame/group")
    public List<ColaRoleMember> checkSameGroup(@RequestParam String roleId, @RequestParam String groupId) {
        return rolememberService.checkSameGroup(roleId, groupId);
    }
}
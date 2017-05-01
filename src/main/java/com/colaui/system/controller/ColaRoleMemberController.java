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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page<ColaRoleMember> paging(@RequestParam int pageSize,
                                       @RequestParam int pageNo, @RequestParam(required = false) String contain) {
        return rolememberService.getPage(pageSize, pageNo, contain);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public void save(@RequestBody ColaRoleMember rolemember) {
        rolememberService.save(rolemember);
    }

    @RequestMapping(value = "/user/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public void saveRoleUser(@RequestBody Map<String, Object> params) {
        String roleId = (String) params.get("roleId");
        ArrayList<String> roleUserIds = (ArrayList<String>) params.get("roleUserIds");
        rolememberService.saveRoleUser(roleId, roleUserIds);
    }

    @RequestMapping(value = "/position/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public void saveRolePosition(@RequestBody Map<String, Object> params) {
        String roleId = (String) params.get("roleId");
        ArrayList<String> rolePositionIds = (ArrayList<String>) params.get("rolePositionIds");
        rolememberService.saveRolePosition(roleId, rolePositionIds);
    }

    @RequestMapping(value = "/dept/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public void saveRoleDept(@RequestBody Map<String, Object> params) {
        String roleId = (String) params.get("roleId");
        ArrayList<String> roleDeptIds = (ArrayList<String>) params.get("roleDeptIds");
        rolememberService.saveRoleDept(roleId, roleDeptIds);
    }

    @RequestMapping(value = "/group/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public void saveRoleGroup(@RequestBody Map<String, Object> params) {
        String roleId = (String) params.get("roleId");
        ArrayList<String> roleGroupIds = (ArrayList<String>) params.get("roleGroupIds");
        rolememberService.saveRoleGroup(roleId, roleGroupIds);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") String id) {
        rolememberService.delete(id);
    }

    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public void deleteByUsername(@RequestParam String roleId,@RequestParam String username) {
        rolememberService.deleteByUsername(roleId, username);
    }

    @RequestMapping(value = "/position/", method = RequestMethod.GET)
    public void deleteByPositionId(@RequestParam String roleId,@RequestParam String positionId) {
        rolememberService.deleteByPositionId(roleId, positionId);
    }

    @RequestMapping(value = "/dept/", method = RequestMethod.GET)
    public void deleteByDeptId(@RequestParam String roleId,@RequestParam String deptId) {
        rolememberService.deleteByDeptId(roleId, deptId);
    }

    @RequestMapping(value = "/group/", method = RequestMethod.GET)
    public void deleteByGroupId(@RequestParam String roleId,@RequestParam String groupId) {
        rolememberService.deleteByGroupId(roleId, groupId);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public void update(@RequestBody ColaRoleMember rolemember) {
        rolememberService.update(rolemember);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.GET)
    public ColaRoleMember find(@PathVariable("id") String id) {
        return rolememberService.find(id);
    }

    @RequestMapping(value = "/{from}/{limit}", method = RequestMethod.GET)
    public List<ColaRoleMember> find(@PathVariable("from") int from,
                                     @PathVariable("limit") int limit) {
        return rolememberService.find(from, limit);
    }

    @RequestMapping(value = "/checksame/user/", method = RequestMethod.GET)
    public List<ColaRoleMember> checkSameUser(@RequestParam String roleId, @RequestParam String username) {
        return rolememberService.checkSameUser(roleId, username);
    }

    @RequestMapping(value = "/checksame/position/", method = RequestMethod.GET)
    public List<ColaRoleMember> checkSamePosition(@RequestParam String roleId, @RequestParam String positionId) {
        return rolememberService.checkSamePosition(roleId, positionId);
    }

    @RequestMapping(value = "/checksame/dept/", method = RequestMethod.GET)
    public List<ColaRoleMember> checkSameDept(@RequestParam String roleId, @RequestParam String deptId) {
        return rolememberService.checkSameDept(roleId, deptId);
    }

    @RequestMapping(value = "/checksame/group/", method = RequestMethod.GET)
    public List<ColaRoleMember> checkSameGroup(@RequestParam String roleId, @RequestParam String groupId) {
        return rolememberService.checkSameGroup(roleId, groupId);
    }
}